package com.excel.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.excel.ExcelHand;

public class DemoMain {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {

		ExcelHand<Map> excelHand = new ExcelHand<Map>();
		System.out.println("==========开始============");
		
		SimpleDateFormat dd = new SimpleDateFormat("HH:mm:ss");//设置日期格式
		System.out.println(dd.format(new Date()));
		
		excelHand.readExcel2003("C:\\Users\\long\\Desktop\\11_3\\test.xls");
		
		List<Map> mapList = new ArrayList<Map>();
		mapList = excelHand.getResult();
		
		System.out.println("==========结果============");	
		
		//SimpleDateFormat dd = new SimpleDateFormat("HH:mm:ss");//设置日期格式
		System.out.println(dd.format(new Date()));
	}
}
