package com.otherbage;

import com.classes.Test_F;

public class Test2 {
	/*	public String name;
	protected int age;
	private int high;
	String sex;*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test_F tf =	new Test_F();
		tf.name ="zoulong";

		tf.setAge(3);
		tf.setSex("Ů");
		System.out.println(tf.name+tf.getClass().getClassLoader());
	}

}
