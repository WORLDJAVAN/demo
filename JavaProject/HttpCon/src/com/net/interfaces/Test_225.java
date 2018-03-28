package com.net.interfaces;

import com.net.connect.HttpClient;
import com.net.dataSecurity.DataSecurity;

public class Test_225 {
	/**
	 * 简单寄件
	 * @param ip
	 * @param accunt_no
	 * @param type
	 * @return
	 */
	public String check_sampeSuit(String ip, String accunt_no,String type) {
		try {
			StringBuffer requestBuffer = new StringBuffer();
			requestBuffer.append("<params>").append("<password>zoulong</password>");
			requestBuffer.append("<accountId>" + accunt_no + "</accountId>");
			requestBuffer.append("<reqtype>"+type+"</reqtype>");
			requestBuffer.append("<suit>10</suit>");
			requestBuffer.append("<tradeid>O-yd003</tradeid>");
			requestBuffer.append("</params>");
			String xmldata = requestBuffer.toString();

			/* 数字签名 */
			String response_str;
			String data = DataSecurity.security("password", "validation","xmldata", ip, "zoulong", xmldata);
			response_str = HttpClient.post("http://127.0.0.1:8080/ydmb-bsm/service/score_manage/score_request.reg",data);
			return response_str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 复杂寄件方式
	 * @param ip
	 * @param accunt_no
	 * @param type
	 * @return
	 */
	public String check_complexScort(String ip, String accunt_no, String type) {
		try {
			StringBuffer requestBuffer = new StringBuffer();
			requestBuffer.append("<params>");
			requestBuffer.append("<password>zoulong</password>");
			requestBuffer.append("<accountId>" + accunt_no + "</accountId>");
			requestBuffer.append("<moneytype>1</moneytype>");
			requestBuffer.append("<costmoney>100</costmoney>");
			requestBuffer.append("<reqtype>"+type+"</reqtype>");
			requestBuffer.append("<suit>8</suit>");
			requestBuffer.append("<tradeid>O-yd003</tradeid>");
			requestBuffer.append("</params>");
			String xmldata = requestBuffer.toString();

			/* 数字签名 */
			String response_str;
			String data = DataSecurity.security("password", "validation","xmldata", ip, "zoulong", xmldata);
			response_str = HttpClient.post("http://192.168.1.225:7001/ydmb-bsm/service/score_manage/score_request.reg",data);
			return response_str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 消费积分
	 * @param ip
	 * @param accunt_no
	 * @return
	 */
	public String check_costScort(String ip, String accunt_no,String type) {
		try {
			StringBuffer requestBuffer = new StringBuffer();
			requestBuffer.append("<params>");
			requestBuffer.append("<password>zoulong</password>");
			requestBuffer.append("<accountId>" + accunt_no + "</accountId>");
			requestBuffer.append("<moneytype>0</moneytype>");
			requestBuffer.append("<reqtype>"+type+"</reqtype>");			
			requestBuffer.append("<costmoney>100</costmoney>");
			requestBuffer.append("<tradeid>O-yd002</tradeid>");
			requestBuffer.append("</params>");
			String xmldata = requestBuffer.toString();

			/* 数字签名 */
			String response_str;
			String data = DataSecurity.security("password", "validation","xmldata", "10.20.57.158", ip, xmldata);
			response_str = HttpClient.post("http://192.168.1.225:7001/ydmb-bsm/service/score_manage/score_request.reg",data);
			return response_str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 其他消费积分
	 * @param ip
	 * @param accunt_no
	 * @param money
	 * @param type
	 * @return
	 */
	public String check_otherScort(String ip, String accunt_no, int money,String type) {
		try {
			StringBuffer requestBuffer = new StringBuffer();
			requestBuffer.append("<params>");
			requestBuffer.append("<password>zoulong</password>");
			requestBuffer.append("<accountId>" + accunt_no + "</accountId>");
			requestBuffer.append("<moneytype>1</moneytype>");
			requestBuffer.append("<reqtype>"+type+"</reqtype>");			
			requestBuffer.append("<costmoney>"+money+"</costmoney>");
			requestBuffer.append("<tradeid>O-yd002</tradeid>");
			requestBuffer.append("</params>");
			String xmldata = requestBuffer.toString();

			/* 数字签名 */
			String response_str;
			String data = DataSecurity.security("password", "validation","xmldata", ip, "zoulong", xmldata);
			response_str = HttpClient.post("http://192.168.1.225:7001/ydmb-bsm/service/score_manage/score_request.reg",data);
			return response_str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 积分消费
	 * @param ip
	 * @param accunt_no
	 * @param money
	 * @param type
	 * @return
	 */
	public String check_Scort2Money(String ip, String accunt_no, String type) {
		try {
			StringBuffer requestBuffer = new StringBuffer();
			requestBuffer.append("<params>");
			requestBuffer.append("<password>zoulong</password>");
			requestBuffer.append("<accountId>" + accunt_no + "</accountId>");
			requestBuffer.append("<reqtype>"+type+"</reqtype>");			
			requestBuffer.append("<convertmoney>100</convertmoney>");
			requestBuffer.append("<tradeid>O-yd003</tradeid>");
			requestBuffer.append("<signal>0</signal>");
			requestBuffer.append("</params>");
			String xmldata = requestBuffer.toString();
			/* 数字签名 */
			String response_str;
			String data = DataSecurity.security("password", "validation","xmldata", ip, "zoulong", xmldata);
			response_str = HttpClient.post("http://192.168.1.225:7001/ydmb-bsm/service/score_manage/score_request.reg",data);
			return response_str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 打折接口
	 * @param ip
	 * @param accunt_no
	 * @return
	 */
	public String check_discount(String ip, String accunt_no,String type) {
		try {
			StringBuffer requestBuffer = new StringBuffer();
			requestBuffer.append("<params>");
			requestBuffer.append("<password>zoulong</password>");
			requestBuffer.append("<accountId>" + accunt_no + "</accountId>");
			requestBuffer.append("<reqtype>"+type+"</reqtype>");			
			requestBuffer.append("<totalmoney>27</totalmoney>");
			requestBuffer.append("<signal>2</signal>");
			requestBuffer.append("</params>");
			String xmldata = requestBuffer.toString();
		
			/* 数字签名 */
			String response_str;
			String data = DataSecurity.security("password", "validation","xmldata", ip, "zoulong", xmldata);
			response_str = HttpClient.post("http://192.168.1.225:7001/ydmb-bsm/service/score_manage/discount_request.reg",data);
			return response_str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 帐户查询
	 * @param ip
	 * @param accunt_no
	 * @return
	 */
	public String check_account(String ip, String accunt_no) {
		try {
			StringBuffer requestBuffer = new StringBuffer();
			requestBuffer.append("<params>");
			requestBuffer.append("<password>zoulong</password>");
			requestBuffer.append("<accountId>" + accunt_no + "</accountId>");
			requestBuffer.append("</params>");
			String xmldata = requestBuffer.toString();
		
			/* 数字签名 */
			String response_str;
			String data = DataSecurity.security("password", "validation","xmldata", ip, "zoulong", xmldata);
			response_str = HttpClient.post("http://192.168.1.225:7001/ydmb-bsm/service/score_manage/account_request.reg",data);
			return response_str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 帐户注册 
	 * @param ip
	 * @param accunt_no
	 * @param type
	 * @return
	 */
	public String check_regist(String ip, String accunt_no,String type) {
		try {
			StringBuffer requestBuffer = new StringBuffer();
			requestBuffer.append("<params>").append("<password>zoulong</password>");
			requestBuffer.append("<accountId>" + accunt_no + "</accountId>");
			requestBuffer.append("<reqtype>004</reqtype>");
			requestBuffer.append("<othercount>1</othercount>");
			requestBuffer.append("<tradeid>O-yd003</tradeid>");
			requestBuffer.append("</params>");
			String xmldata = requestBuffer.toString();
		
			/* 数字签名 */
			String response_str;
			String data = DataSecurity.security("password", "validation","xmldata", ip, "zoulong", xmldata);
			response_str = HttpClient.post("http://192.168.1.225:7001/ydmb-bsm/service/score_manage/score_request.reg",data);
			return response_str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
