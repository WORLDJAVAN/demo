package com.excel.inter;

import java.util.List;
import java.util.Map;

public interface IHandleGroup {
	/**
	 * 
	 * @param rs_value 线程处理分组的结果
	 * @return 进行封装的结果
	 */
	public List<Map> handleGroup(List<Map> rs_value);
}
