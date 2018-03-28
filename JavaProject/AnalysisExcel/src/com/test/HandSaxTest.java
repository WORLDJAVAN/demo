package com.test;

import com.excel.HandExcelSax;

public class HandSaxTest {

	public static void main(String[] args) {
		try {
			HandExcelSax excelSax = new HandExcelSax();
			excelSax.readOneSheet("C:\\Users\\long\\Desktop\\待处理\\test.xlsx");
			//excelSax.process("C:\\Users\\long\\Desktop\\待处理\\test.xlsx");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
