package com.base.web_class;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddress_T {

	public static void main(String[] args) {
		
		getLocalIp();
	}
	
	@SuppressWarnings("static-access")
	public static void getLocalIp(){
		InetAddress myIp = null;
		try {
			// ��ȡ����IP��ַ
			System.out.println(myIp.getLocalHost());			
			// ��ȡ����IP��ַ
			System.out.println(myIp.getByName("http://gss.yundasys.com"));
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
