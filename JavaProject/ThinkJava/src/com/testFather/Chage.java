package com.testFather;

public class Chage {
	
   Father father = new Father(), happy = new Happy_F(), sad = new Sad_F();
   
   public void changeMode(String signal){
	   if (signal.equals("happy")){   
	   father = new Happy_F();}
	   else father = new Sad_F();
   }
   
   public void execute(){
	   father.actor();
   }
   
   public void changeWay(int num){
	   if (num==1) 
		   father = happy; 
	   if (num==2) 
		   father = sad;
   }
}
