package com.excel.inter;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;


/**
 * ������չ����ҵ��ʵ�ֽӿ�
 * 
 * @author long
 * 
 */
public interface IHandleBuz {
	/**
	 * ���񱣻�ģʽ
	 * @param task ��������������
	 * @return
	 */
	public Map handleSingle(HSSFRow task);

	/**
	 * ���񲢷�ģʽ
	 * @param task ��������������
	 * @return
	 */
	public Map handleBusiness(HSSFRow task);
}
