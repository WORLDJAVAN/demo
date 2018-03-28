package com.entity;

import java.util.Date;

import com.annotation.JoeAnotation;

public class ManyFields {
    
	@JoeAnotation(Id = "p_id", table = "tb_Person",condition="tb_Person.p_id=tb_other.b_id")
	private Integer personId;

	@JoeAnotation(column = "p_name")
	private String personName;

	@JoeAnotation(column = "p_brithday")
	private Date brithday;

	@JoeAnotation(Id = "b_id", table = "tb_other", condition="tb_cc.p_id=tb_dd.b_id")
	private String a;

	@JoeAnotation(column = "b_bbb")
	private String b;
	
	@JoeAnotation(column = "b_ccc")
	private String c;
	
	@JoeAnotation(column = "b_ddd")
	private String d;

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public Date getBrithday() {
		return brithday;
	}

	public void setBrithday(Date brithday) {
		this.brithday = brithday;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}
}
