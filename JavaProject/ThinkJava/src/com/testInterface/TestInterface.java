package com.testInterface;

public class TestInterface {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
       ChooseActor ca = new ChooseActor();
       Actor ac = ca.chose("happ");
       ac.actor();
       System.out.println("另一种调用方式");
       ca.change("happy").actor();
	}

}
