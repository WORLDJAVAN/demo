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
      
      System.out.println("��һ��ʹ�÷�ʽ");
      chage.changeWay(2);
      chage.execute();
      chage.changeWay(1);
      chage.execute();
	}

}
