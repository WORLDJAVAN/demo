package com.stolen.inter.imp;

import com.stolen.inter.IHandleBuz;


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
	public synchronized Object handleSingle(Object task){
		return handleBusiness(task);
	}
	
	/**
	 * 任务并发模式
	 * @param task 待处理的任务对象
	 * @param 处理结果返回值
	 */
	public abstract Object handleBusiness(Object task);
	
}
