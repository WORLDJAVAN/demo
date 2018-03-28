package com.unite;

public class Happy_U extends Father {

	@Override
	public IFunction getFunction(int num) {
		// TODO Auto-generated method stub
		if (num==1) return this;
		
		return null;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		System.out.println("子类实现Happy功能");
	}
	
	

}
