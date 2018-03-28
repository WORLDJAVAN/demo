package com.observer;

import java.util.Observable;

public class Fruits extends Observable {
	private String name;
	private float price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name; // 设置变化点
		setChanged();
		notifyObservers(name);
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price; // 设置变化点
		setChanged();
		notifyObservers(new Float(price));
	}

}
