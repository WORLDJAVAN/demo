package com.observer;

import java.util.Observable;
import java.util.Observer;

public class Name_AObserver implements Observer {
	private String name=null;
	
	@Override
	public void update(Observable o, Object arg) {
		
		if (arg instanceof String){
			name=(String)arg; //产品名称改变值在name中 
			System.out.println("Name_AObserver :name changet to "+name);
			}
		
		if (arg instanceof Product){
			Product t =(Product) arg;
			System.out.println("Test"+t.getName()+t.getPrice());
		}
	}

}
