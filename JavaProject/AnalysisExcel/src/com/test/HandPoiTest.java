package com.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.excel.HandExcelPOI;

public class HandPoiTest {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		HandExcelPOI handexcel = new HandExcelPOI();
		List<Map> mapList = new ArrayList<Map>();
		try {
			mapList = handexcel
					.readExcel2007("C:\\Users\\long\\Desktop\\待处理\\test.xlsx");
			mapList = handexcel
					.readExcel2003("C:\\Users\\long\\Desktop\\待处理\\test.xls");
			for (Map map : mapList) {
				String value = (String) map.get("zz");
				String values = (String) map.get("zoulong");
				System.out.println(value);
				System.out.println(values);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
