package com.test;

import com.stolen.inter.imp.AHandleBuz;

public class DHandleBuz extends AHandleBuz {

	@Override
	public Object handleBusiness(Object task) {	
		String result = "<order>" + Thread.currentThread().getName() + ":"
				+ task + "</order>";	
		String result2 = "<JJJ>" + Thread.currentThread().getName() + ":"
				+ task + "</JJJ>";		
		return result+result2;
	}
}
