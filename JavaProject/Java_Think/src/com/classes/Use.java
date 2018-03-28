package com.classes;

public class Use {
	
/*	public String name;
	protected int age;
	private int high;
	String sex;*/

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		/*Test_F tf =	new Test_F();
		tf.name ="zoulong";
		tf.age =3;
		tf.sex="Ů";
		System.out.println(tf.name+tf.age+tf.sex);*/
		
		Userf uf = new Userf();
		Object oo = uf;
		System.out.println(oo.getClass().getCanonicalName());
		Child1 child1 = new Child1();
		oo=child1;
		System.out.println(oo.getClass().getCanonicalName());
		Child1.cry();
		
		child1.say();
		child1.cry();
	   child1.mm();
		uf.use2(new Child2());
	}
}
