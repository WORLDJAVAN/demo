package com.grade;

import java.util.Map;

public interface ResumeGrade {
	/**
	 * 配置主--子XML文件信息
	 * @param xmlmaster 主类xml文件名
	 * @param xmldetail 各分类xml文件名
	 */
	public void setXMLFileName(String xmlmaster,String xmldetail);
	/**
	 * 获得简历评分接口
	 * @param paramkey 存放计算简历积分参数,Map中的Key表示所选项项目，value值表示对应项目的选值
	 *  如 map<"en","1"> 表示英语水平项目，用户选择了第二项的值(大学四级)
	 * @return
	 */
	public float getGrade(Map<String,String> paramkey);
}
