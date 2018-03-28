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
			// 获取本机IP地址
			System.out.println(myIp.getLocalHost());			
			// 获取网络IP地址
			System.out.println(myIp.getByName("http://gss.yundasys.com"));
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
