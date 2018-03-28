package com.base.web_class;

import java.io.DataInputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 * @version URL类的使有用方法
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
			// 方式一：
			URL url_one = new URL("http://www.163.com/mail.html");

			// 方式二：
			URL url = new URL("http://www.163.com");
			URL urldoc = new URL(url, "mail.html");

			// 方式三：
			URL urlpro = new URL("http", "www.163.com", "/mail.html");

			// URL 主要的方法如下：
			url_one.getProtocol(); // 获得协议
			url_one.getPort(); // 获得端口
			url_one.getRef(); // 获得URL标记
			url_one.getHost(); // 返回URL主机名
			url_one.getFile(); // 返回URL文件名及路径

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取网络文件
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
			URL url_file = new URL(file_path); // 可以是本地的资源如：file:///d:/test/test.txt
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
