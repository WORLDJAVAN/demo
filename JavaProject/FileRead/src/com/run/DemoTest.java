package com.run;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.readfile.ReadFileDemo;
import com.readfile.ReadProp;

public class DemoTest {
	public static void main(String[] args) {
		
		/* ��properties�ļ� */		
		String serverHost = ReadProp.getServerHost();
		String serverPort = ReadProp.getServerPort();
		String userEmail = ReadProp.getUserEmail();
		String password = ReadProp.getPassword();		
		System.out.println(serverHost+serverPort+userEmail+password);
		
		/* �ļ���ȡ����  */
		ReadFileDemo.readByStream("txt_d.txt", "GBK");
		ReadFileDemo.readByStream("xml_c.xml", "UTF-8");
		
		ReadFileDemo.readXMLSample("xml_s.xml");
		ReadFileDemo.readXMLComplex("xml_c.xml");
		
        /* ����̨�Ľ��� */
		try {
			System.out.println("����������");
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String name = in.readLine();
			System.out.println("�㰮������:"+name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
