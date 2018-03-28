package com.testFather;

public class TestFahter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
      Chage chage = new Chage();
      chage.execute();
      
      chage.changeMode("happy");
      chage.execute();
      
      chage.changeMode("other");
      chage.execute();
      
      System.out.println("另一种使用方式");
      chage.changeWay(2);
      chage.execute();
      chage.changeWay(1);
      chage.execute();
	}

}
