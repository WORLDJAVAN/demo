package com.stolen.inter;


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
	public Object handleSingle(Object task);

	/**
	 * 任务并发模式
	 * @param task 待处理的任务对象
	 * @return
	 */
	public Object handleBusiness(Object task);
}
