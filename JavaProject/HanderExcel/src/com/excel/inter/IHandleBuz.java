package com.excel.inter;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;


/**
 * 用于扩展具体业务实现接口
 * 
 * @author long
 * 
 */
public interface IHandleBuz {
	/**
	 * 任务保护模式
	 * @param task 待处理的任务对象
	 * @return
	 */
	public Map handleSingle(HSSFRow task);

	/**
	 * 任务并发模式
	 * @param task 待处理的任务对象
	 * @return
	 */
	public Map handleBusiness(HSSFRow task);
}
