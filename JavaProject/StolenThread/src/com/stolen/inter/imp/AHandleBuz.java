package com.stolen.inter.imp;

import com.stolen.inter.IHandleBuz;


/**
 * 
 * @author JOE
 *
 */
public abstract class AHandleBuz implements IHandleBuz {
	
	/**
	 * ���񱣻�ģʽ
	 * @param task ��������������
	 * @param  ����������ֵ
	 */
	public synchronized Object handleSingle(Object task){
		return handleBusiness(task);
	}
	
	/**
	 * ���񲢷�ģʽ
	 * @param task ��������������
	 * @param ����������ֵ
	 */
	public abstract Object handleBusiness(Object task);
	
}
