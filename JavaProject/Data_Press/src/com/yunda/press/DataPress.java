package com.yunda.press;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataPress {
	/**
	 * 通过传入的日期格式文件返回三位字符表示
	 * @param date 传入的日期参数 如：2014-05-06
	 * @return
	 */
	public String getstr(Date date){
		
		 SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		 System.out.println(format.format(new Date()));
		return null;
	}
	
	/**
	 * 通过字串还原成日期格
	 * @param str 传入的日期压缩后字串 如：1AA
	 * @return
	 */
	public Date getDate(String str){
		return null;
	}
}
