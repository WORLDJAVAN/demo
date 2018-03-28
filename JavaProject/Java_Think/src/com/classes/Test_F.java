package com.classes;

public class Test_F {
	public String name;
	protected int age;
	private int high;
	String sex;
	public Test_F tf;
	
	public Test_F test(){
		if (tf.equals(null)){
			
		}
		
		tf = new Test_F();
		tf.high =3;
		
		return tf;
	}
	
	public int getAge() {
		return age;	
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getHigh() {
		return high;
	}
	public void setHigh(int high) {
		this.high = high;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
}
