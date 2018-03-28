package com.test;

import java.util.Date;

import com.analysis.Ano_Analysis;
import com.entity.Man;

public class Test {

	@org.junit.Test
	public void test() {
		Man man = new Man();
		man.setPersonId(1);
		man.setPersonName("limiang");
		man.setBrithday(new Date());
		try {
			System.out.println(Ano_Analysis.findSQL(man.getClass(), "1=df and 4=4"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@org.junit.Test
	public void test2() {


		/*ManyFields many = new ManyFields();
		try {
			System.out.println(Ano_Analysis.findSQL(many.getClass()));
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}
