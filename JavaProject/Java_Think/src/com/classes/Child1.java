package com.classes;

public class Child1 extends Father  {

	public  static void cry() {		
		System.out.println("Child1-cry");
	}
	public  void mm() {
	   super.say();
		System.out.println("Child1-cry");
	}	
}
