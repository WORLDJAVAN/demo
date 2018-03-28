package com.test;

public class TestClass {
	private String name;
	@SuppressWarnings("unused")
	private int age;

	public String setNumb(String name){
		this.name = name;
		System.out.println("单一name方法");
		return this.name;
	}
	
	public void setNumb(String name, int age){
		System.out.println("调用二个参数方法");
		this.setNumb(name);
		this.age =age;	
		System.out.println("22"+name+age);
	}
}
