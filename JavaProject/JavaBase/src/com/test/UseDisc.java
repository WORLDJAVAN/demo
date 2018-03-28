package com.test;

public  class UseDisc {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DiscInteface disc1 = DiscFactory.getInstance("Õý°æ");
		disc1.sell();
		
		DiscInteface disc2 = DiscFactory.getInstance("µÁ°æ");
		disc2.sell();
	}

}
