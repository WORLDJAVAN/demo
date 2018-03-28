package com.unite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

public class TestU {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vector<Object> vector = new Vector<Object>();
		vector.add("love");
		vector.add("you");
		vector.addElement("I love you taoyinghua");
		print(vector.iterator());
		
       List<Object> b = new ArrayList<Object>();
       b.add("zoulong");
       b.add("taoyinghua");
       b.add(1314);
        print( b.iterator());
      
      Stack<String> stack = new Stack<String>();
      
      if((Boolean) stack.add("taoyinghua"))
     System.out.println("ÒÑ³É¹¦");
      
      String result = (String)stack.push("zoulong");
      System.out.println(result);
      
      while(!stack.empty()){
    	  System.out.println(stack.pop());
      }
	}

	public static void print(Iterator<Object> e){
		while (e.hasNext()){
			System.out.println(e.next().toString());
			
		}
	}
}
