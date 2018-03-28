package com.mainrun;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.codec.binary.Base64;

public class  EnDecoder {

	private static String getValue(Map<String, String> data,String key){
		String value = (String)data.get(key);
		value = value==null?"":value;
		return value;
	}
	
	public static String encode(Map<String, String> data) {
		if (data == null || data.isEmpty()) {
			return null;
		}	
		try{		
			StringBuffer query = new StringBuffer();
			
			String partnerid = "partnerid";
			String xmldata = "xmldata";
			String password = "password";
			String v_partnerid = getValue(data,partnerid);
			String v_xmldata = getValue(data,xmldata);
			String v_password = getValue(data,password);
			
			String url = security(partnerid,xmldata,"validation",v_partnerid,v_password,v_xmldata);
			
			Set<Entry<String, String>> entries = data.entrySet();
			boolean hasParam = false;		
			for (Entry<String, String> entry : entries) {				
				String name = entry.getKey();				
				if(partnerid.equals(name)||xmldata.equals(name)||password.equals(name))
					continue;				
				String value = entry.getValue();
				// 忽略参数名或参数值为空的参数				
				if (!"".equals(name)&&!"".equals(value)) {
					if (hasParam) {
						query.append("&");
					} else {
						hasParam = true;
					}
					query.append(name).append("=").append(URLEncoder.encode(value,"UTF-8"));
				}
			}			
			if(query.length()>0){
				query.append("&");
			}
			query.append(url);
					
			return query.toString();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}

	/***
	 * 订单数据加密
	 * @param name1 合作商字段名�?
	 * @param name2 数据字段名称
	 * @param name3 验证字段名称
	 * @param partnerid 合作商ID
	 * @param data 源数�?
	 * @param password 密码
	 * @return 加密后的数据
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 */
	private static String security(String name1, String name2, String name3, String partnerid, String password, String data) 
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		data = new String(new Base64().encode(data.getBytes("UTF-8")));
				
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
	
	/**
	 * md5加密方法
	 * @param source 源字符串
	 * @return 加密后的字符�?
	 * @throws NoSuchAlgorithmException
	 */
	private static  String md5(String source) throws NoSuchAlgorithmException {		
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