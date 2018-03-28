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
 * @param <T> ����List<T>�������ͷ�ʽ
 */
public class RunnableExcel implements Runnable {

	private BlockingQueue<Location> taskQueue; //�������ҵ��ָ����
	private HSSFSheet sheet; // �����嵥�б�
	private int blockAmount; //��������ִ�е�������
	private CountDownLatch signal;

	/**
	 * ��ʼ���޷��ؽ����
	 * @param taskList �������ҵ������б�
	 * @param handleBuz ��ע���ҵ��(T����)������
	 * @param handleMode ҵ����ģʽ True:����ģʽ False:����ģʽ
	 * @param taskQueue ҵ���зֵķָ�����
	 * @param taskCount ���ָ�������,ִ��������
	 */
	public RunnableExcel(HSSFSheet sheet, BlockingQueue<Location> taskQueue, int blockAmount,CountDownLatch signal) {
		this.sheet = sheet;
		this.taskQueue = taskQueue;
		this.blockAmount = blockAmount;
		this.signal=signal;
	}
    /**
     * Runnable�����Ʒ���
     */
	public void run() {
		try {		
			while (blockAmount > 0) {
				Location locSpot = taskQueue.take();
				int first = locSpot.getFirst();
				int last = locSpot.getLast();

				for (int i = first; i <= last; i++) {
					Map<String, Object> val=new HashMap<String, Object>();
					
					//��ȡ��i����������
					HSSFRow row = sheet.getRow(i);
					
					//������ݹ�����
					int cells = row.getPhysicalNumberOfCells();
					// ������
					for (int j = 0; j < cells; j++) {
						// ��ȡ���е�ֵ&shy;
						try {
							HSSFCell cell = row.getCell(j);
							String cellValue = getCellValue(cell);
							System.out.println(Thread.currentThread().getName()+"��ȡֵ:"+cellValue+":��"+i+"������");
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
