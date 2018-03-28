package com.excel.multi;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.excel.Location;
import com.excel.inter.imp.AHandleBuz;

/**
 * 
 * @author JOE
 *
 * @param <T> 传入List<T>对象类型泛式
 */
public class RunnableExcel implements Runnable {

	private BlockingQueue<Location> taskQueue; //待处理的业务分割队列
	private HSSFSheet sheet; // 任务清单列表
	private int blockAmount; //控制任务执行的批次数
	private CountDownLatch signal;

	/**
	 * 初始化无返回结果类
	 * @param taskList 待处理的业务对象列表
	 * @param handleBuz 待注入的业务(T对象)处理类
	 * @param handleMode 业务处理模式 True:并发模式 False:保护模式
	 * @param taskQueue 业务切分的分割点队列
	 * @param taskCount 被分割后组的数,执行批次数
	 */
	public RunnableExcel(HSSFSheet sheet, BlockingQueue<Location> taskQueue, int blockAmount,CountDownLatch signal) {
		this.sheet = sheet;
		this.taskQueue = taskQueue;
		this.blockAmount = blockAmount;
		this.signal=signal;
	}
    /**
     * Runnable主控制方法
     */
	public void run() {
		try {		
			while (blockAmount > 0) {
				Location locSpot = taskQueue.take();
				int first = locSpot.getFirst();
				int last = locSpot.getLast();

				for (int i = first; i <= last; i++) {
					Map<String, Object> val=new HashMap<String, Object>();
					
					//获取第i行数据内容
					HSSFRow row = sheet.getRow(i);
					
					//获得数据共列数
					int cells = row.getPhysicalNumberOfCells();
					// 遍历列
					for (int j = 0; j < cells; j++) {
						// 获取到列的值&shy;
						try {
							HSSFCell cell = row.getCell(j);
							String cellValue = getCellValue(cell);
							System.out.println(Thread.currentThread().getName()+"读取值:"+cellValue+":第"+i+"行数据");
						} catch (Exception e) {
							e.printStackTrace();		
						}
					}
				}
				blockAmount--;
			}
			signal.countDown();
					
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
