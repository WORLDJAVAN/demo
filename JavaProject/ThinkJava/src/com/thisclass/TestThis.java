package com.thisclass;

public class TestThis {
	
	 private int i = 0;  
	 
	 TestThis increment() { 
		 int count=0;
		 count++;
		 System.out.println("count = "+count);
		 i++;     
		 return this;   
		 }   
	 
	 void print() {    
		 System.out.println("i = " + i);  
		 } 

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestThis x = new TestThis();    
		x.increment().increment().increment().print(); 
	}

}
