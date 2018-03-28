package com.base64;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DataSecurity {
	public static String security(String value_data) {
		// 1.�Դ������ݽ���ת��
		value_data = new String(new Base64().encode(value_data.getBytes()));		
		System.out.println(value_data+"one");
		return null;		
	}
	
	//����С���Ǳ� Base64(MD5(PASWD))��Ϣ
	public static String B_MD5(String req_Value) {		
		// �Բ�������Base 64���� ,���MD5����Ϣ
		String result = null;
		try {
			byte[] paswd = new BASE64Decoder().decodeBuffer(req_Value);
			for(int i = 0; i<paswd.length;i++){
				System.out.print(paswd[i]);				
			}
			System.out.println("---zoulong----");
					
			BASE64Encoder encoder = new BASE64Encoder();	
			result = encoder.encode(paswd);	
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}		
		return result;
	}
	
	public static void main(String[] arg){
		
		System.out.println("------ͳһ��Ȩ����-------");
		String Original = Original_MD5("zoulong");
		System.out.println(Original);
		
		System.out.println("------������Ϣ-------");
		String req_value = "ZjJhY2U5YmMyZmUxOGQyYmVjNzY1MjViYWJkYjJiYjg=";		
		System.out.println(req_value);
		
		System.out.println("------������Ϣ-------");
		String dealStr = B_MD5(req_value);
		System.out.println(dealStr);
	}
	
	public static String Original_MD5(String srcValue) {
		byte[] bytPwd = srcValue.getBytes();
		MessageDigest alg = null;
		String tgtValue = null;
		try {			
			alg = MessageDigest.getInstance("MD5");
			alg.update(bytPwd);
			byte[] digest = alg.digest();		
			BASE64Encoder encoder = new BASE64Encoder();
			tgtValue = encoder.encode(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return tgtValue;
	}
}
