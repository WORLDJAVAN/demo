package com.test;


public class TestC {

	public static void main(String[] args) {
		TestClass tt = new TestClass(){
			@Override
			public String setNumb(String name){
				System.out.println("pppp"+super.setNumb(name));
				return name;
			}
		};
		
		tt.setNumb("kk");
	}

}
