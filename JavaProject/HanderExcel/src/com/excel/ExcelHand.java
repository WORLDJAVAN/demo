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
 * 读取Excel操作
 * 
 * @author long
 * 
 */
public class ExcelHand<T> {
	
	private int blockAmount = 1; // Excel数量切分块数
	private int blockSize;
	private int totalRows;
	
	private Boolean islegal = true; // 参数合法性校验
	
	HSSFSheet sheet;
	
	final BlockingQueue<Location> taskQueue = new ArrayBlockingQueue<Location>(20);
	@SuppressWarnings("rawtypes")
	private List<Map> cacheResult = new ArrayList<Map>(); // 用于返回结果的List对象

	/**
	 * 注入拦截处理方法
	 */
	public ExcelHand(){
		
	}
	/**
	 * 读取2003及以前版本文件,扩展名为: xls
	 */
	public void readExcel2003(String filePath) {
		CountDownLatch signal = new CountDownLatch(1);
		FileInputStream fileInStr=null;
	
		//获得文件输入流
		try {
			fileInStr=new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		HSSFWorkbook wookbook;
		try {
			wookbook = new HSSFWorkbook(fileInStr);
			// 在Excel文档中，第一张工作表的缺省索引是0
			 sheet = wookbook.getSheetAt(0);
			
			// 获取到Excel文件中的所有行数
			 totalRows = sheet.getPhysicalNumberOfRows();
			 blockAmount=600;
			 if(checkParams()){			 
				 blockSize = totalRows / blockAmount;			 
				 /* 执行任务切分动作存放到阻塞队列中 */
					new Thread() {
						@Override
						public void run() {
							putTaskQueue(blockAmount, blockSize);
						}
					}.start();
					/* 启动,并设置线程池的大小 */
					excue(this.sheet); 
			 }		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 测试多线程切割方法
	 */
	private void excue(HSSFSheet sheet){
		
		/* 执行任务的线程池 */
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
	 * 执行任务切分操作
	 * @param count 任务切分后的数量
	 * @param size 计算出的任务块大小
	 */
	private void putTaskQueue(int count, int size){
		Location locSpot;
		/* 开始执行任务切分 */
		for (int i = 0; i < count; i++) {
			if (i == count - 1)
				locSpot = new Location(size * i, totalRows - 1);
			else
				locSpot = new Location(size * i, size * (i + 1) - 1);

			/* 存放任务的位置信息点 */
			try {
				taskQueue.put(locSpot);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 用户参数合法性校验
	 * @return 不可执行时,设置返回 False
	 */
	private Boolean checkParams(){
		if ((totalRows <= 0) || (blockAmount <= 0))
			islegal = false;
		return islegal;
	}
	
}
