package com.test;

import java.util.ArrayList;
import java.util.List;

import com.stolen.inter.imp.HandleGroupImp;

public class DHandleGroup extends HandleGroupImp {

	@Override
	public List<Object> handleGroup(List<Object> rs_value) {
		
		StringBuffer temp= new StringBuffer("<Orders>");		
		List<Object> listnew = new ArrayList<Object>();	
		
		for(Object obj:rs_value){
			temp.append(obj);
		}
		temp.append("</Orders>");
		listnew.add(temp);
		
		return listnew;
	}

}
