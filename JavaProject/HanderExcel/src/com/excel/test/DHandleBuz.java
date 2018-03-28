package com.excel.test;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;

import com.excel.inter.imp.AHandleBuz;

public class DHandleBuz extends AHandleBuz {

	@Override
	public Map handleBusiness(HSSFRow task) {	
		 System.out.println("handleBusiness");
		 
		 HSSFRow row = (HSSFRow) task;
		 Map<Integer, String> keys = new HashMap<Integer, String>();
		
		// 获取到Excel文件中的所有的列
			int cells = row.getPhysicalNumberOfCells();
			// 遍历列
			for (int j = 0; j < cells; j++) {
				// 获取到列的值&shy;
				try {
					HSSFCell cell = row.getCell(j);
					String cellValue = getCellValue(cell);
					keys.put(j,cellValue);						
				} catch (Exception e) {
					e.printStackTrace();	
				}
			}
		return keys;
	}
	
	private  String getCellValue(HSSFCell cell) {
		DecimalFormat df = new DecimalFormat("#");
		String cellValue=null;
		if (cell == null)
			return null;
		switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC:
				if(HSSFDateUtil.isCellDateFormatted(cell)){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					cellValue=sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
					break;
				}
				cellValue=df.format(cell.getNumericCellValue());
				break;
			case HSSFCell.CELL_TYPE_STRING:			
				cellValue=String.valueOf(cell.getStringCellValue());
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				cellValue=String.valueOf(cell.getCellFormula());
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				cellValue=null;
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				cellValue=String.valueOf(cell.getBooleanCellValue());
				break;
			case HSSFCell.CELL_TYPE_ERROR:
				cellValue=String.valueOf(cell.getErrorCellValue());
				break;
		}
		if(cellValue!=null&&cellValue.trim().length()<=0){
			cellValue=null;
		}
		return cellValue;
	}
}
