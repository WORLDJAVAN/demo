package com.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

/**
 * <pre>
 *   Title: YdDataSecurity.java
 *   Description: 数据加密工具类
 *   Project:member project
 *   Copyright: yundaex.com Copyright (c) 2013
 *   Company: shanghai yundaex
 * </pre>
 * 
 * @author xiongfang
 * @version 2.0
 * @date 2013-4-28
 */
public class DataSecurity {
	
	/***
	 * 订单数据加密
	 * @param name1 合作商字段名称
	 * @param name2 数据字段名称
	 * @param name3 验证字段名称
	 * @param partnerid 合作商ID
	 * @param data 源数据
	 * @param password 密码
	 * @return 加密后的数据
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 */
	public static String security(String name1, String name2, String name3, String partnerid, String password, String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		data = new String(new Base64().encode(data.getBytes()));
		
		// 签名内容 = base64(data) + partnerid + password;
		String validation = data + partnerid + password;
		validation = md5(validation);
		
		partnerid = URLEncoder.encode(partnerid, "UTF-8");
		data = URLEncoder.encode(data, "UTF-8");
		validation = URLEncoder.encode(validation, "UTF-8");
		
		return new StringBuffer().append(name1).append("=")
								 .append(partnerid).append("&").append(name2).append("=")
								 .append(data).append("&").append(name3).append("=").append(validation).toString();
	}
	
	/***
	 * 订单数据加密
	 * @param s_password 调用积分接口密码名称
	 * @param s_validation 签名名称
	 * @param s_data 源数据名称
	 * @param p_ip 调用积分接口IP
	 * @param value_data 源数据
	 * @param value_password 密码
	 * @return 加密后的数据
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 */
	public static String security4Points(String s_password, String s_validation, String s_data, String p_ip, String value_password, String value_data)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// 1.对传输数据进行转换
		value_data = new String(new Base64().encode(value_data.getBytes()));
		
		// 2.签名内容：base64(data)+ p_id + password			
		String validation = md5(value_data + p_ip + value_password);		

		// 3.对要传输的数据进行UTF-8编码转换
		value_password = URLEncoder.encode(value_password, "UTF-8");		
		value_data = URLEncoder.encode(value_data, "UTF-8");				
		validation = URLEncoder.encode(validation, "UTF-8");		
		
		// 4.返回签名与加密字串
		return new StringBuffer().append(s_password).append("=").append(value_password)
				.append("&").append(s_validation).append("=").append(validation).append("&")
				.append(s_data).append("=").append(value_data).toString();
	}
	
	
	/**
	 * md5加密方法
	 * @param source 源字符串
	 * @return 加密后的字符串
	 * @throws NoSuchAlgorithmException
	 */
	public static String md5(String source) throws NoSuchAlgorithmException {
		
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',  
                'a', 'b', 'c', 'd', 'e', 'f' };
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(source.getBytes());
		byte[] tmp = md.digest();
		char[] str = new char[16 * 2];
		
		int k = 0;
		for (int i = 0; i < 16; i++) {
			byte byte0 = tmp[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		
		return new String(str);
	}
}
