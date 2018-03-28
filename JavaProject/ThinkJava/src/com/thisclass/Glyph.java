package com.thisclass;

public abstract class Glyph {
  abstract void draw();
  
  public Glyph() {
	System.out.println("父类初始化开始");
	draw();
	System.out.println("父类初始化完成");
  }
}
