package com.grade;

import java.util.Map;

public interface ResumeGrade {
	/**
	 * ������--��XML�ļ���Ϣ
	 * @param xmlmaster ����xml�ļ���
	 * @param xmldetail ������xml�ļ���
	 */
	public void setXMLFileName(String xmlmaster,String xmldetail);
	/**
	 * ��ü������ֽӿ�
	 * @param paramkey ��ż���������ֲ���,Map�е�Key��ʾ��ѡ����Ŀ��valueֵ��ʾ��Ӧ��Ŀ��ѡֵ
	 *  �� map<"en","1"> ��ʾӢ��ˮƽ��Ŀ���û�ѡ���˵ڶ����ֵ(��ѧ�ļ�)
	 * @return
	 */
	public float getGrade(Map<String,String> paramkey);
}
