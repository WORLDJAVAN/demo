package com.base.web_class;

import java.io.DataInputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 * @version URL���ʹ���÷���
 * @author long
 * 
 */
public class URL_T {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// readlocalFile("file:///d://test//test.txt");
		 readWebFile() ;
	}

	@SuppressWarnings("unused")
	public static void creat_URL() {
		try {
			// ��ʽһ��
			URL url_one = new URL("http://www.163.com/mail.html");

			// ��ʽ����
			URL url = new URL("http://www.163.com");
			URL urldoc = new URL(url, "mail.html");

			// ��ʽ����
			URL urlpro = new URL("http", "www.163.com", "/mail.html");

			// URL ��Ҫ�ķ������£�
			url_one.getProtocol(); // ���Э��
			url_one.getPort(); // ��ö˿�
			url_one.getRef(); // ���URL���
			url_one.getHost(); // ����URL������
			url_one.getFile(); // ����URL�ļ�����·��

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ��ȡ�����ļ�
	 */
	@SuppressWarnings("deprecation")
	public static void readWebFile() {
		try {
			URL url_path = new URL("http://www.163.com");
			DataInputStream dips = new DataInputStream(url_path.openStream());

			String inpstr;

			while ((inpstr = dips.readLine()) != null) {

				System.out.println(inpstr);
			}
			dips.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	static void readlocalFile(String file_path) {
		try {
			URL url_file = new URL(file_path); // �����Ǳ��ص���Դ�磺file:///d:/test/test.txt
			DataInputStream datastream = new DataInputStream(
					url_file.openStream());

			String inpstr;
			while ((inpstr = datastream.readLine()) != null) {

				System.out.println(inpstr);
			}
			datastream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
