package com.thisclass;

public class RoundGlyph extends Glyph {
	int radius = 1; 
	static int  sign = 8;
	
	 RoundGlyph(int r) {     
		 radius = r;     
		 System.out.println("RoundGlyph.RoundGlyph(), radius = "+ radius);  
		 draw();
		 }  
	 
	void draw() {
		System.out.println("RoundGlyph.draw(), radius = " + radius);  
		System.out.println("RoundGlyph.draw(), static_sign = " + sign); 
	}

	 public static void main(String[] args) {    
		 new RoundGlyph(5);   
		 } 
}
