package com.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.excel.inter.IHandleGroup;
import com.excel.inter.imp.AHandleBuz;
import com.excel.multi.RunnableExcel;

/**
 * ��ȡExcel����
 * 
 * @author long
 * 
 */
public class ExcelHand<T> {
	
	private int blockAmount = 1; // Excel�����зֿ���
	private int blockSize;
	private int totalRows;
	
	private Boolean islegal = true; // �����Ϸ���У��
	
	HSSFSheet sheet;
	
	final BlockingQueue<Location> taskQueue = new ArrayBlockingQueue<Location>(20);
	@SuppressWarnings("rawtypes")
	private List<Map> cacheResult = new ArrayList<Map>(); // ���ڷ��ؽ����List����

	/**
	 * ע�����ش�����
	 */
	public ExcelHand(){
		
	}
	/**
	 * ��ȡ2003����ǰ�汾�ļ�,��չ��Ϊ: xls
	 */
	public void readExcel2003(String filePath) {
		CountDownLatch signal = new CountDownLatch(1);
		FileInputStream fileInStr=null;
	
		//����ļ�������
		try {
			fileInStr=new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		HSSFWorkbook wookbook;
		try {
			wookbook = new HSSFWorkbook(fileInStr);
			// ��Excel�ĵ��У���һ�Ź������ȱʡ������0
			 sheet = wookbook.getSheetAt(0);
			
			// ��ȡ��Excel�ļ��е���������
			 totalRows = sheet.getPhysicalNumberOfRows();
			 blockAmount=600;
			 if(checkParams()){			 
				 blockSize = totalRows / blockAmount;			 
				 /* ִ�������зֶ�����ŵ����������� */
					new Thread() {
						@Override
						public void run() {
							putTaskQueue(blockAmount, blockSize);
						}
					}.start();
					/* ����,�������̳߳صĴ�С */
					excue(this.sheet); 
			 }		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * ���Զ��߳��и��
	 */
	private void excue(HSSFSheet sheet){
		
		/* ִ��������̳߳� */
		ExecutorService service = Executors.newFixedThreadPool(6);
		CountDownLatch signal = new CountDownLatch(1);
		try {
			RunnableExcel runexcel = new RunnableExcel(this.sheet,taskQueue,blockAmount,signal);
			for (int count = 0; count < 6; count++) {
				service.submit(runexcel);
			}
			signal.await();
			service.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public List<Map> getResult(){
		return islegal ? cacheResult : null;
	}
	
	/**
	 * ִ�������зֲ���
	 * @param count �����зֺ������
	 * @param size �������������С
	 */
	private void putTaskQueue(int count, int size){
		Location locSpot;
		/* ��ʼִ�������з� */
		for (int i = 0; i < count; i++) {
			if (i == count - 1)
				locSpot = new Location(size * i, totalRows - 1);
			else
				locSpot = new Location(size * i, size * (i + 1) - 1);

			/* ��������λ����Ϣ�� */
			try {
				taskQueue.put(locSpot);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * �û������Ϸ���У��
	 * @return ����ִ��ʱ,���÷��� False
	 */
	private Boolean checkParams(){
		if ((totalRows <= 0) || (blockAmount <= 0))
			islegal = false;
		return islegal;
	}
	
}
