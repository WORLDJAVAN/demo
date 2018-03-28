package com.testInterface;

public class ChooseActor {
   public Actor chose(String signal){
	   if (signal.equals("happy")) {
		  return new HappyActor(); 
	   }
	   else  return new SadActor();
   }
   
   public Actor change(String signal){
	   Actor actor=null;
	   if(signal.equals("happy")){
		   actor = new HappyActor();
	   }
	   else actor = new SadActor();
	   return actor;
   }
}
