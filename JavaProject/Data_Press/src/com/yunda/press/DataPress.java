package com.yunda.press;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataPress {
	/**
	 * ͨ����������ڸ�ʽ�ļ�������λ�ַ���ʾ
	 * @param date ��������ڲ��� �磺2014-05-06
	 * @return
	 */
	public String getstr(Date date){
		
		 SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		 System.out.println(format.format(new Date()));
		return null;
	}
	
	/**
	 * ͨ���ִ���ԭ�����ڸ�
	 * @param str ���������ѹ�����ִ� �磺1AA
	 * @return
	 */
	public Date getDate(String str){
		return null;
	}
}
