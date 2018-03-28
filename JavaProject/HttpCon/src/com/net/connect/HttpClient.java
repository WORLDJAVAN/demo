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
		// �˴���urlConnection����ʵ�����Ǹ���URL������Э,���ɵ�URLConnection�������HttpURLConnection,�ʴ˴���ý���ת��
		// ΪHttpURLConnection���͵Ķ���,�Ա��õ�HttpURLConnection�����API.����:
		HttpURLConnection connection =(HttpURLConnection)geturl.openConnection();
		
		/* HttpURLConnection�������  */
		
	    // �����Ƿ���httpUrlConnection�������Ϊ�����post���󣬲���Ҫ���� Http�����ڣ������Ҫ��Ϊtrue, Ĭ���������false; 
		connection.setDoOutput(true);
	    // �����Ƿ��httpUrlConnection���룬Ĭ���������true;  
		connection.setDoInput(true);
		connection.setRequestProperty("Connection", "Keep-Alive"); 
		// Post ������ʹ�û��� 
		connection.setUseCaches(false); 
		// �趨����ķ���Ϊ"POST"��Ĭ����GET  
		if (type) connection.setRequestMethod("POST");
		// �趨���ӳ�ʱ��
		connection.setConnectTimeout(10000);
		// �趨�ļ���������
		connection.setRequestProperty("Content-type", contentType);   
		connection.connect();	
		
		// ����ͨ����������󹹽����������������ʵ����������л��Ķ���      
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
		
		// ����������д�����ݣ���Щ���ݽ��浽�ڴ滺������   
		out.write(data);   
		// ˢ�¶�������������κ��ֽڶ�д��Ǳ�ڵ����У�Щ��ΪObjectOutputStream��   
		out.flush();   
		// �ر������󡣴�ʱ������������������д���κ����ݣ���ǰд������ݴ������ڴ滺������, �ڵ����±ߵ�getInputStream()����ʱ�Ű�׼���õ�http������ʽ���͵�������   
		out.close();   
		// ����HttpURLConnection���Ӷ����getInputStream()����,���ڴ滺�����з�װ�õ�������HTTP������ķ��͵�����ˡ�  
		// �ϱߵ�httpConn.getInputStream()�����ѵ���,����HTTP�����ѽ���,�±�����������������������壬   
		// ��ʹ���������û�е���close()�������±ߵĲ���Ҳ��������������д���κ�����.   
		// ��ˣ�Ҫ���·�������ʱ��Ҫ���´������ӡ���������������´�������������д����   
		// ��������������
		
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
	 * Get��ʽ�ύ
	 */
	public static String get(String url, String data)throws Exception {
        return postData(url,data,CONTENT_TYPE_FORM,false);
	}
}
