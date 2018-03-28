package com.observer;

import java.util.Observable;

public class Product extends Observable {
	private String name;
	private float price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name; // 设置变化点
		setChanged();
		notifyObservers(new String("观察传递的字串"));
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price; // 设置变化点
		setChanged();
		notifyObservers(this);
	}

}
