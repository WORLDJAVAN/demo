package com.observer;

import java.util.Observable;

public class Product extends Observable {
	private String name;
	private float price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name; // ���ñ仯��
		setChanged();
		notifyObservers(new String("�۲촫�ݵ��ִ�"));
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price; // ���ñ仯��
		setChanged();
		notifyObservers(this);
	}

}
