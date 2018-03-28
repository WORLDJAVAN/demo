package com.loader;

import java.io.File;

public class JoeLoader extends ClassLoader {
	  public JoeLoader() { 
	      try {
			super.findClass(getMyURLs());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	   } 

	  private static String getMyURLs() { 
	    try {     	
	    	return new File("D:/Trains/JavaProject/Dynamic-Class/classes/").toString();  
	    } catch (Exception e) { 
	       e.printStackTrace(); 
	       return null; 
	    } 
	  } 
}
