package com.stolen.inter;

import java.util.List;

public interface IHandleGroup {
	/**
	 * 
	 * @param rs_value 线程处理分组的结果
	 * @return 进行封装的结果
	 */
	public List<Object> handleGroup(List<Object> rs_value);
}
