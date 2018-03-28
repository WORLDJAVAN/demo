package com.observer;

public class TestControul {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException {
		Product product = new Product();
		Fruits fruit = new Fruits();
		
		Name_AObserver obs_a = new Name_AObserver();
		Name_BObserver obs_b = new Name_BObserver();
		Price_Observer priceobs = new Price_Observer();

		product.addObserver(obs_a);
		product.addObserver(obs_b);
		product.addObserver(priceobs);
		
		fruit.addObserver(obs_a);
		fruit.addObserver(obs_b);
		fruit.addObserver(priceobs);

		product.setName(new String("����"));
		fruit.setName(new String("ƻ��"));
		
		product.setPrice(new Float(3.15));
		fruit.setPrice(new Float(4.15));
		
	    product.deleteObserver(obs_b);

		System.out.println("==========���Ըı�Ĺ۲�==========");

		Thread.currentThread().sleep(3000);

		product.setName(new String("����_AA"));
		product.setPrice(new Float(3.15));
	}

}
