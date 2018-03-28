package com.test;

public class Outer {
	public  void ourtest(String name){
		System.out.println(name);
	}
  class Inner {
	void testin(){
		Outer ou = new Outer();
		ou.ourtest("zhangshan");
	}	  
  }
  
}
