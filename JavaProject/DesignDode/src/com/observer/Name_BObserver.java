package com.observer;

import java.util.Observable;
import java.util.Observer;

public class Name_BObserver implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		
		System.out.println("Name_BObserver--��ǰ�������ǣ�"+o.getClass().getName());	
		
		if(o instanceof Product){
			System.out.println("Name_BObserver--�����ഥ��");
		}		
		
		if(o instanceof Fruits){
			System.out.println("Name_BObserver--ˮ���ഥ��");
		}			
	}
}
