package com.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.common.DataSecurity;
import com.entity.ParamEntity;

@Controller
public class Request_Controller {
	private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(100, 400,
			100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3000),
			new ThreadPoolExecutor.CallerRunsPolicy());

	private ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 5,
			TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2),
			new ThreadPoolExecutor.CallerRunsPolicy());

	@RequestMapping("/hello/service__")
	public String receiveScoreRequest(HttpServletRequest request,
			final HttpServletResponse response) {
		final String request_ip = request.getRemoteAddr();
		final String request_data = request.getParameter("xmldata");
		final String validation = request.getParameter("validation");
		final String password = request.getParameter("password");
		final SignalResult signal = new SignalResult();
		signal.resultMap.put(validation, true);
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				StringBuffer responseBuffer = new StringBuffer();
				ParamEntity param = null;
				Element element = null;
				String resultStr = null;

				// 1. 校验请求是否合法
				try {
					String valida = DataSecurity.md5(request_data + request_ip
							+ password);
					if (!valida.equals(validation)) {
						responseBuffer.append("验证非法出错");
						response.setCharacterEncoding("UTF-8");
						try {
							response.getWriter().write(
									responseBuffer.toString());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					String xmldata = new String(Base64
							.decodeBase64(request_data));
					SAXReader saxReader = new SAXReader();
					Document document = saxReader
							.read(new ByteArrayInputStream(xmldata
									.getBytes("UTF-8")));
					element = document.getRootElement();
				} catch (Exception ex) {
					responseBuffer.append("验证非法出错");
					ex.printStackTrace();
					response.setCharacterEncoding("UTF-8");
					try {
						response.getWriter().write(responseBuffer.toString());
						throw new Exception("验证非法出错");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// 2. 解析请求内容封装到实体Bean: ParamEntity
				Node password = null; // 用户需要验证的密码
				Node accountId = null; // 操作用户的帐号信息
				Node suit = null; // 用户寄件数
				Node costmoney = null; // 用于积分的消费金额
				Node changemoney = null; // 需要积分兑换的金额
				Node moneyType = null; // 用于记录消费类型, 0:充值 1:其他消费
				Node urlType = null; // 用于记录需求类型

				try {
					param = new ParamEntity();
					password = element.selectSingleNode("password");
					accountId = element.selectSingleNode("accountId");
					suit = element.selectSingleNode("suit");
					costmoney = element.selectSingleNode("costmoney");
					urlType = element.selectSingleNode("urlType");

					param.setIP(request_ip);
					param.setAccountId(accountId == null ? "" : accountId
							.getText());
					param.setChangemoney(changemoney == null ? 0 : Integer
							.parseInt(changemoney.getText()));
					param.setCostmoney(costmoney == null ? 0 : Integer
							.parseInt(costmoney.getText()));
					param.setMoneyType(moneyType == null ? "" : moneyType
							.getText());
					param.setPassword(password == null ? "" : password
							.getText());
					param.setSuit(suit == null ? 0 : Integer.parseInt(suit
							.getText()));
					param.setUrlType(urlType == null ? "" : urlType.getText());

					// 模拟调用积分算法
					resultStr = runcore(param);

				} catch (Exception ex) {
					responseBuffer.append("请求参数出错");
					ex.printStackTrace();
					try {
						throw new Exception("请求参数解析出错");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// 返回到客户端
				responseBuffer.append(resultStr);
				response.setCharacterEncoding("UTF-8");
				try {
					response.getWriter().write(responseBuffer.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
				signal.put_result(validation, false);
			}
		});

		System.out.println(signal.get_result(validation));

		while (signal.get_result(validation)) {
			System.out.println("进入等待");
			try {
				System.out.println(Thread.currentThread().getName()
						+ "说：我,去取结果!!!");

				if (!signal.get_result(validation)) {
					System.out.println("我拿到结果了，兄弟们我先回去了");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName() + "兄弟们我先回去了");
		signal.resultMap.remove(validation);
		return null;
	}

	private String runcore(final ParamEntity param) {
		int ticket = 10;
		String Str = null;
		for (int i = 1; i < 10; i++) {
			Str = Thread.currentThread().getName() + "说:"
					+ param.getAccountId() + "您的积分是：" + i;
			ticket = ticket - 1;
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Str + " 您的票为：" + ticket);
		}
		return param.getAccountId() + "以返回" + Str;
	}

	class SignalResult {
		private Map<String, Boolean> resultMap = new HashMap<String, Boolean>();

		private synchronized void put_result(String key, Boolean value) {
			resultMap.put(key, value);
		}

		private synchronized Boolean get_result(String key) {
			if (!resultMap.get(key)) {
				return false;
			}
			return true;
		}
	}

	// =================对比测试=====================

	@RequestMapping("/hello/service-xx")
	public String receiveScore(HttpServletRequest request,
			HttpServletResponse response) {
		String request_ip = request.getRemoteAddr();
		String request_data = request.getParameter("xmldata");
		String validation = request.getParameter("validation");
		String password = request.getParameter("password");
		SignalResult signal = new SignalResult();

		StringBuffer responseBuffer = new StringBuffer();
		ParamEntity param = null;
		Element element = null;
		String resultStr = null;

		// 1. 校验请求是否合法
		try {
			String valida = DataSecurity.md5(request_data + request_ip
					+ password);
			if (!valida.equals(validation)) {
				responseBuffer.append("验证非法出错");
				response.setCharacterEncoding("UTF-8");
				try {
					response.getWriter().write(responseBuffer.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			String xmldata = new String(Base64.decodeBase64(request_data));
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new ByteArrayInputStream(xmldata
					.getBytes("UTF-8")));
			element = document.getRootElement();
		} catch (Exception ex) {
			responseBuffer.append("验证非法出错");
			ex.printStackTrace();
			response.setCharacterEncoding("UTF-8");
			try {
				response.getWriter().write(responseBuffer.toString());
				throw new Exception("验证非法出错");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 2. 解析请求内容封装到实体Bean: ParamEntity
		Node password1 = null; // 用户需要验证的密码
		Node accountId = null; // 操作用户的帐号信息
		Node suit = null; // 用户寄件数
		Node costmoney = null; // 用于积分的消费金额
		Node changemoney = null; // 需要积分兑换的金额
		Node moneyType = null; // 用于记录消费类型, 0:充值 1:其他消费
		Node urlType = null; // 用于记录需求类型

		try {
			param = new ParamEntity();
			password1 = element.selectSingleNode("password");
			accountId = element.selectSingleNode("accountId");
			suit = element.selectSingleNode("suit");
			costmoney = element.selectSingleNode("costmoney");
			urlType = element.selectSingleNode("urlType");

			param.setIP(request_ip);
			param.setAccountId(accountId == null ? "" : accountId.getText());
			param.setChangemoney(changemoney == null ? 0 : Integer
					.parseInt(changemoney.getText()));
			param.setCostmoney(costmoney == null ? 0 : Integer
					.parseInt(costmoney.getText()));
			param.setMoneyType(moneyType == null ? "" : moneyType.getText());
			param.setPassword(password1 == null ? "" : password1.getText());
			param.setSuit(suit == null ? 0 : Integer.parseInt(suit.getText()));
			param.setUrlType(urlType == null ? "" : urlType.getText());

			// 模拟调用积分算法
			resultStr = runcore(param);

		} catch (Exception ex) {
			responseBuffer.append("请求参数出错");
			ex.printStackTrace();
			try {
				throw new Exception("请求参数解析出错");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 返回到客户端
		responseBuffer.append(resultStr);
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(responseBuffer.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		signal.put_result(validation, false);

		System.out.println(signal.get_result(validation));
		System.out.println(Thread.currentThread().getName() + "兄弟们我先回去了");
		return null;
	}

	@RequestMapping("/hello/service")
	public String receiveTest(final HttpServletRequest request, final HttpServletResponse response) {
		System.out.println(Thread.currentThread().getName()+"说：我到了");
		
		StringBuffer responseBuffer = new StringBuffer();
		Future<String> result = pool.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				// *************
				String request_ip = request.getRemoteAddr();
				String request_data = request.getParameter("xmldata");
				String validation = request.getParameter("validation");
				String password = request.getParameter("password");
				ParamEntity param = null;
				Element element = null;
				String resultStr = null;

				// 1. 校验请求是否合法
				try {
					String valida = DataSecurity.md5(request_data + request_ip + password);
					if (!valida.equals(validation)) {				
					  return resultStr="验证非法出错";
					}
					String xmldata = new String(Base64
							.decodeBase64(request_data));
					SAXReader saxReader = new SAXReader();
					Document document = saxReader
							.read(new ByteArrayInputStream(xmldata
									.getBytes("UTF-8")));
					element = document.getRootElement();
				} catch (Exception ex) {
					return resultStr="验证非法出错";				
				}
				// 2. 解析请求内容封装到实体Bean: ParamEntity
				Node password1 = null; // 用户需要验证的密码
				Node accountId = null; // 操作用户的帐号信息
				Node suit = null; // 用户寄件数
				Node costmoney = null; // 用于积分的消费金额
				Node changemoney = null; // 需要积分兑换的金额
				Node moneyType = null; // 用于记录消费类型, 0:充值 1:其他消费
				Node urlType = null; // 用于记录需求类型

				try {
					param = new ParamEntity();
					password1 = element.selectSingleNode("password");
					accountId = element.selectSingleNode("accountId");
					suit = element.selectSingleNode("suit");
					costmoney = element.selectSingleNode("costmoney");
					urlType = element.selectSingleNode("urlType");

					param.setIP(request_ip);
					param.setAccountId(accountId == null ? "" : accountId
							.getText());
					param.setChangemoney(changemoney == null ? 0 : Integer
							.parseInt(changemoney.getText()));
					param.setCostmoney(costmoney == null ? 0 : Integer
							.parseInt(costmoney.getText()));
					param.setMoneyType(moneyType == null ? "" : moneyType
							.getText());
					param.setPassword(password1 == null ? "" : password1
							.getText());
					param.setSuit(suit == null ? 0 : Integer.parseInt(suit
							.getText()));
					param.setUrlType(urlType == null ? "" : urlType.getText());

					// 模拟调用积分算法
					resultStr = runcore(param);

				} catch (Exception ex) {
					return resultStr="请求参数出错";
				}
				System.out.println(Thread.currentThread().getName()+ "我处理完了，接其他活了:-)");
				// *************
				return resultStr;
			}
		});

		System.out.println(Thread.currentThread().getName()+"说：我在等结果");
		try {
			responseBuffer.append(result.get());
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(responseBuffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
