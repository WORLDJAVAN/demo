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
	 * ���񱣻�ģʽ
	 * @param task ��������������
	 * @param  ����������ֵ
	 */
	public synchronized Map handleSingle(HSSFRow task){
		return handleBusiness(task);
	}
	
	/**
	 * ���񲢷�ģʽ
	 * @param task ��������������
	 * @param ����������ֵ
	 */
	public abstract Map handleBusiness(HSSFRow task);
	
}
