package com.stolen.inter;


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
	public Object handleSingle(Object task);

	/**
	 * ���񲢷�ģʽ
	 * @param task ��������������
	 * @return
	 */
	public Object handleBusiness(Object task);
}
