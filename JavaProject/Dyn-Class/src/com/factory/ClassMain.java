package com.factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ClassMain {

	public static void main(String[] args) {
   try {   	
    	
	  //    控制台的交互 
		try {
			System.out.println("请输入名字");
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String name = in.readLine();
			System.out.println("你爱的人是:"+name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	   System.out.println(System.getProperty("java.class.path"));
	   System.setIn(new FileInputStream(new File("D:/Trains/JavaProject/Dynamic-Class/classes/JOE-BMW-1.0.jar")));
	  
	   String className = "com.factory.imp.BMWFactory";
			try {
				ICarFactory icf = (ICarFactory) ClassLoader.getSystemClassLoader().loadClass(className).newInstance();
				
				/*JoeLoader joeLoader = new JoeLoader();
				
				ICarFactory icf = (ICarFactory)	new JoeLoader().loadClass(className).newInstance();*/
							
			//	ICarFactory icf = (ICarFactory)	
			//	ICarFactory icf = (ICarFactory) Class.forName(className).newInstance();
				icf.makeCar();
			} catch (ClassNotFoundException e) {				
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
