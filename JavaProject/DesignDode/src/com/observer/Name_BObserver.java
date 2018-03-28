package com.observer;

import java.util.Observable;
import java.util.Observer;

public class Name_BObserver implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		
		System.out.println("Name_BObserver--当前触发类是："+o.getClass().getName());	
		
		if(o instanceof Product){
			System.out.println("Name_BObserver--生产类触发");
		}		
		
		if(o instanceof Fruits){
			System.out.println("Name_BObserver--水果类触发");
		}			
	}
}
