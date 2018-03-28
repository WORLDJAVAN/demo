package com.excel.inter.imp;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;

import com.excel.inter.IHandleBuz;


/**
 * 
 * @author JOE
 *
 */
public abstract class AHandleBuz implements IHandleBuz {
	
	/**
	 * 任务保护模式
	 * @param task 待处理的任务对象
	 * @param  处理结果返回值
	 */
	public synchronized Map handleSingle(HSSFRow task){
		return handleBusiness(task);
	}
	
	/**
	 * 任务并发模式
	 * @param task 待处理的任务对象
	 * @param 处理结果返回值
	 */
	public abstract Map handleBusiness(HSSFRow task);
	
}
