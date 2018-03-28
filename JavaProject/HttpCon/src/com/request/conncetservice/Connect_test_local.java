package com.request.conncetservice;

import com.net.connect.HttpClient;
import com.net.dataSecurity.DataSecurity;

public class Connect_test_local {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		StringBuffer requestBuffer = new StringBuffer();
		requestBuffer.append("<params>");
		requestBuffer.append("<password>xiongfang</password>");
		requestBuffer
				.append("<accountId>116e3d1438ac47cb9d9e271a6eaa509b</accountId>");
		requestBuffer.append("<moneytype>1</moneytype>");
		requestBuffer.append("<reqtype>001</reqtype>");
		requestBuffer.append("<tradeid>13739386427086578</tradeid>");
		requestBuffer.append("<costmoney>0</costmoney>");
		requestBuffer.append("<suit>1</suit>");
		requestBuffer.append("<tradeid>13739386427086578</tradeid>");
		requestBuffer.append("</params>");
		String xmldata = requestBuffer.toString();

		try {
			String data = DataSecurity.security("password", "validation",
					"xmldata", "10.20.58.169", "", xmldata);
			System.out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void check_authScort(String custm_id) {
		try {
			StringBuffer requestBuffer = new StringBuffer();
			requestBuffer.append("<params>").append(
					"<password>zoulong</password>");
			requestBuffer.append("<accountId>" + custm_id + "</accountId>");
			requestBuffer.append("<moneytype>0</moneytype>").append(
					"<reqtype>002</reqtype>");
			requestBuffer.append("<costmoney>100</costmoney>");
			requestBuffer.append("<suit>0</suit>").append(
					"<convertmoney>0</convertmoney>");
			requestBuffer.append("<tradeid>O-003</tradeid>");
			requestBuffer.append("</params>");
			String xmldata = requestBuffer.toString();

			/* 数字签名 */
			String response_str;
			String data = DataSecurity.security("password", "validation",
					"xmldata", "127.0.0.1", "zoulong", xmldata);
			response_str = HttpClient
					.post("http://127.0.0.1:8080/ydmb-bsm/service/score_manage/score_request.reg",
							data);
			System.out.println(response_str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void check_acountScort(String custm_id) {
		try {
			StringBuffer requestBuffer = new StringBuffer();
			requestBuffer.append("<params>").append(
					"<password>zoulong</password>");
			requestBuffer.append("<accountId>" + custm_id + "</accountId>")
					.append("</params>");
			String xmldata = requestBuffer.toString();
			/* 数字签名 */
			String response_str;
			String data = DataSecurity.security("password", "validation",
					"xmldata", "10.20.57.158", "zoulong", xmldata);
			response_str = HttpClient
					.post("http://10.20.57.158:8080/ydmb-bsm/service/score_manage/account_request.reg",
							data);
			System.out.println(response_str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检测积分打折
	 * 
	 * @param custm_id
	 * @param total_money
	 */
	public static void check_discount(String custm_id, String total_money) {
		try {
			StringBuffer requestBuffer = new StringBuffer();
			requestBuffer.append("<params>").append(
					"<password>zoulong</password>");
			requestBuffer.append("<accountId>" + custm_id + "</accountId>");
			requestBuffer.append("<signal>1</signal>");
			requestBuffer
					.append("<totalmoney>" + total_money + "</totalmoney>")
					.append("</params>");
			String xmldata = requestBuffer.toString();

			/* 数字签名 */
			String response_str;
			String data = DataSecurity.security("password", "validation",
					"xmldata", "10.20.57.158", "zoulong", xmldata);
			response_str = HttpClient
					.post("http://10.20.57.158:8080/ydmb-bsm/service/score_manage/discount_request.reg",
							data);
			System.out.println(response_str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
