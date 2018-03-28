package com.net.connect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {
	public static final String CONTENT_TYPE_JSON = "application/json;charset=utf-8";
	public static final String CONTENT_TYPE_XML = "application/xml;charset=utf-8";
	public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded;charset=utf-8";

	public static String postData(String url,String data,String contentType,Boolean type) throws Exception {
		URL geturl = new URL(url);
		// 此处的urlConnection对象实际上是根据URL的请求协,生成的URLConnection类的子类HttpURLConnection,故此处最好将其转化
		// 为HttpURLConnection类型的对象,以便用到HttpURLConnection更多的API.如下:
		HttpURLConnection connection =(HttpURLConnection)geturl.openConnection();
		
		/* HttpURLConnection对象参数  */
		
	    // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在 Http正文内，因此需要设为true, 默认情况下是false; 
		connection.setDoOutput(true);
	    // 设置是否从httpUrlConnection读入，默认情况下是true;  
		connection.setDoInput(true);
		connection.setRequestProperty("Connection", "Keep-Alive"); 
		// Post 请求不能使用缓存 
		connection.setUseCaches(false); 
		// 设定请求的方法为"POST"，默认是GET  
		if (type) connection.setRequestMethod("POST");
		// 设定连接超时间
		connection.setConnectTimeout(10000);
		// 设定文件传输类型
		connection.setRequestProperty("Content-type", contentType);   
		connection.connect();	
		
		// 现在通过输出流对象构建对象输出流对象，以实现输出可序列化的对象。      
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
		
		// 向对象输出流写出数据，这些数据将存到内存缓冲区中   
		out.write(data);   
		// 刷新对象输出流，将任何字节都写入潜在的流中（些处为ObjectOutputStream）   
		out.flush();   
		// 关闭流对象。此时，不能再向对象输出流写入任何数据，先前写入的数据存在于内存缓冲区中, 在调用下边的getInputStream()函数时才把准备好的http请求正式发送到服务器   
		out.close();   
		// 调用HttpURLConnection连接对象的getInputStream()函数,将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。  
		// 上边的httpConn.getInputStream()方法已调用,本次HTTP请求已结束,下边向对象输出流的输出已无意义，   
		// 既使对象输出流没有调用close()方法，下边的操作也不会向对象输出流写入任何数据.   
		// 因此，要重新发送数据时需要重新创建连接、重新设参数、重新创建流对象、重新写数据   
		// 接收输入流数据
		
	/*	URL url = new URL(authContext.getLoginurl() + "&userId=" + userId
				+ "&password=" + password);

		reader = new BufferedReader(new InputStreamReader(url.openStream(),
				"UTF-8"));*/
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
		String line = "";
		StringBuffer buffer = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		reader.close();
		return buffer.toString();
	}

	/***
	 * 
	 * @param url
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, String data) throws Exception {
		return postData(url, data, CONTENT_TYPE_FORM,true);
	}

	/**
	 * Get方式提交
	 */
	public static String get(String url, String data)throws Exception {
        return postData(url,data,CONTENT_TYPE_FORM,false);
	}
}
