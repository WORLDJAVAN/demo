package com.excel;

/**
 * 设置任务的分割起、末标识
 * @author long
 *
 */
public class Location {
	private int first;
	private int last;
	
	public Location(int first, int last){
		this.first = first;
		this.last = last;		
	}

	public int getFirst() {
		return first;
	}

	public int getLast() {
		return last;
	}
}
