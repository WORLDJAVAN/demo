package com.thisclass;

public abstract class Glyph {
  abstract void draw();
  
  public Glyph() {
	System.out.println("�����ʼ����ʼ");
	draw();
	System.out.println("�����ʼ�����");
  }
}
