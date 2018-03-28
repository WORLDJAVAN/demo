package com.classes;

public class Userf {
	@SuppressWarnings("static-access")
	public void use(Father f){
		f.say();
		f.cry();
	}
		
	public void use2(Ifather of){
		of.say();
		of.cry();
	}
}
