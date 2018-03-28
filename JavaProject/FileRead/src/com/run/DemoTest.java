package com.run;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.readfile.ReadFileDemo;
import com.readfile.ReadProp;

public class DemoTest {
	public static void main(String[] args) {
		
		/* 读properties文件 */		
		String serverHost = ReadProp.getServerHost();
		String serverPort = ReadProp.getServerPort();
		String userEmail = ReadProp.getUserEmail();
		String password = ReadProp.getPassword();		
		System.out.println(serverHost+serverPort+userEmail+password);
		
		/* 文件读取测试  */
		ReadFileDemo.readByStream("txt_d.txt", "GBK");
		ReadFileDemo.readByStream("xml_c.xml", "UTF-8");
		
		ReadFileDemo.readXMLSample("xml_s.xml");
		ReadFileDemo.readXMLComplex("xml_c.xml");
		
        /* 控制台的交互 */
		try {
			System.out.println("请输入名字");
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String name = in.readLine();
			System.out.println("你爱的人是:"+name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
