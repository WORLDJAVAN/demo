package com.thread.task;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import com.common.DataSecurity;
import com.entity.ParamEntity;

public class DataDeal implements Runnable {
	String request_ip;
	String request_data;
	String validation;
	String password;
	HttpServletResponse response;

	public void setParam(HttpServletRequest request, HttpServletResponse response) {
		request_ip = request.getRemoteAddr();
		request_data = request.getParameter("xmldata");
		validation = request.getParameter("validation");
		password = request.getParameter("password");
		this.response = response;
	}

	@Override
	public void run() {
		StringBuffer responseBuffer = new StringBuffer();
		ParamEntity param = null;
		Element element = null;

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
			param.setChangemoney(changemoney == null ? 0 : Integer.parseInt(changemoney.getText()));
			param.setCostmoney(costmoney == null ? 0 : Integer.parseInt(costmoney.getText()));
			param.setMoneyType(moneyType == null ? "" : moneyType.getText());
			param.setPassword(password1 == null ? "" : password1.getText());
			param.setSuit(suit == null ? 0 : Integer.parseInt(suit.getText()));
			param.setUrlType(urlType == null ? "" : urlType.getText());

			// 模拟调用积分算法
			System.out.println(Thread.currentThread().getName() + "模拟处理算法");

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
		responseBuffer.append("hhhhhh");
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(responseBuffer.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "兄弟们我先回去了");
		notify();
	}

}
