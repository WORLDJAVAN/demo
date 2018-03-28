package com.observer;

import java.util.Observable;
import java.util.Observer;

public class Price_Observer implements Observer {
	private float price=0;
	
	@Override
	public void update(Observable o, Object arg) {
		
		if (arg instanceof Float){
			price=((Float)arg).floatValue(); 
			System.out.println("PriceObserver :price changet to "+price);
			}
	}

}
