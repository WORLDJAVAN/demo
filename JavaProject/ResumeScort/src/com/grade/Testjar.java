package com.grade;

import java.util.HashMap;
import java.util.Map;

import com.grade.ResumeGrade;
import com.grade.imp.ResumeGradeImp;

public class Testjar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 ResumeGrade test = new ResumeGradeImp();
	       test.setXMLFileName("master.xml", "detail.xml");
	       Map<String,String> resume = new HashMap<String, String>();
	       resume.put("en", "b");
	       resume.put("Reg", "d");
	       resume.put("major", "b");
	       resume.put("edu", "b");
	       resume.put("schinfor", "c");    
	       float res = test.getGrade(resume);
	       
	       System.out.println(res);
	}

}
