package com.yunda.press;

import java.text.SimpleDateFormat;
import java.util.Date;

public class main_test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		 String str = format.format(new Date());
		 System.out.println(str);
	}

}
