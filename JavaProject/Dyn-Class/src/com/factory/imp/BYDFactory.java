package com.factory.imp;

import com.factory.ICarFactory;

public class BYDFactory implements ICarFactory {

	@Override
	public void makeCar() {
		System.out.println("���ǵ���BYD���������������");
	}
}
