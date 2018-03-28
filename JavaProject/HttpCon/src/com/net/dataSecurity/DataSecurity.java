package com.net.dataSecurity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Base64;

public class DataSecurity {
	public static String security(String s_password, String s_validation, String s_data, String p_ip, String value_password, String value_data)
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

	// md5算法
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
