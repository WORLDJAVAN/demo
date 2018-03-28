package com.factory.imp;

import com.factory.ICarFactory;

public class BYDFactory implements ICarFactory {

	@Override
	public void makeCar() {
		System.out.println("这是调用BYD生产类产生的汽车");
	}
}
