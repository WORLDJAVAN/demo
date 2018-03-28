package com.request.conncetservice;

import com.net.connect.HttpClient;
import com.net.dataSecurity.DataSecurity;
import com.net.interfaces.Test_Local;

public class Connect_test_225 {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		for(int j=1;j<3000;j++){
			final int m=j;
			Thread query = new Thread(new Runnable() {		
				@Override
				public void run() {
/*					Integral_Interface test_interface = new Integral_Interface();
				    String result = test_interface.check_otherScort("127.0.0.1", "CS-00"+m,1*m);*/
					
			
				    Test_Local test_interface = new Test_Local();
				     String result = test_interface.check_discount("10.20.57.158", "weijia"+m); //打折接口
				 //  String result = test_interface.check_account("weijia"+m); //帐户查询 ok 1
				    System.out.println(result); 
				}
			});	
			query.start();
		}
		
		// Test_Local test_interface = new Test_Local();
	 //   String result = test_interface.check_account("127.0.0.1", "guhu"); //帐户查询 ok 1
	//	 String result = test_interface.check_regist("127.0.0.1", "CS-001"); // 注册积分 ok
	     
	   //  String result = test_interface.check_complexScort("127.0.0.1", "CS-002"); //复杂寄件 ok	2
	 //    String result = test_interface.check_sampeSuit("127.0.0.1", "CS-xxx"); //简单寄件 3 ok
		// String result = test_interface.check_discount("127.0.0.1", "xxxxxxxxxxxxxxx"); //4.打折查询   ok
	    //   String result = test_interface.check_otherScort("127.0.0.1", "CS-002",5); //5 其他消费  ok
	 //    String result = test_interface.check_costScort("192.168.1.225", "CS-uuu"); // 6 充值消费  ok
      //  String result = test_interface.check_Scort2Money("127.0.0.1", "xxxxx"); //7消费积分 ok

      //   System.out.println(result); 
	}

	public static void check_authScort(String custm_id) {
		try {
			StringBuffer requestBuffer = new StringBuffer();
			requestBuffer.append("<params>").append("<password>zoulong</password>");
			requestBuffer.append("<accountId>"+custm_id+"</accountId>");
			requestBuffer.append("<moneytype>0</moneytype>").append("<reqtype>002</reqtype>");
			requestBuffer.append("<costmoney>100</costmoney>");
			requestBuffer.append("<suit>0</suit>").append("<convertmoney>0</convertmoney>");
			requestBuffer.append("<tradeid>O-003</tradeid>");
			requestBuffer.append("</params>");			
			String xmldata = requestBuffer.toString();		
			
			/* 数字签名 */
			String response_str;		
			String data = DataSecurity.security("password", "validation","xmldata", "127.0.0.1", "zoulong", xmldata);
			response_str = HttpClient.post("http://127.0.0.1:8080/ydmb-bsm/service/score_manage/score_request.reg",data);
			System.out.println(response_str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void check_acountScort(String custm_id) {
		try {
			StringBuffer requestBuffer = new StringBuffer();
			requestBuffer.append("<params>").append("<password>zoulong</password>");
			requestBuffer.append("<accountId>"+custm_id+"</accountId>").append("</params>");				
			String xmldata = requestBuffer.toString();					
			/* 数字签名 */
			String response_str;		
			String data = DataSecurity.security("password", "validation","xmldata", "10.20.57.158", "zoulong", xmldata);
			response_str = HttpClient.post("http://10.20.57.158:8080/ydmb-bsm/service/score_manage/account_request.reg",data);
			System.out.println(response_str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 检测积分打折
	 * @param custm_id
	 * @param total_money
	 */
	public static void check_discount(String custm_id,String total_money) {
		try {
			StringBuffer requestBuffer = new StringBuffer();
			requestBuffer.append("<params>").append("<password>zoulong</password>");
			requestBuffer.append("<accountId>"+custm_id+"</accountId>");
			requestBuffer.append("<signal>1</signal>");
			requestBuffer.append("<totalmoney>"+total_money+"</totalmoney>").append("</params>");			
			String xmldata = requestBuffer.toString();		
			
			/* 数字签名 */
			String response_str;		
			String data = DataSecurity.security("password", "validation","xmldata", "10.20.57.158", "zoulong", xmldata);
			response_str = HttpClient.post("http://10.20.57.158:8080/ydmb-bsm/service/score_manage/discount_request.reg",data);
			System.out.println(response_str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* 积分打折测试
	Thread query = new Thread(new Runnable() {		
		@Override
		public void run() {
			check_discount("CS-002","10");
		}
	});	
	query.start();
	
	Thread query1 = new Thread(new Runnable() {		
		@Override
		public void run() {
			check_discount("CS-003","8");
		}
	});	
	query1.start();*/
	
	// check_acountScort("CS-003");
}
