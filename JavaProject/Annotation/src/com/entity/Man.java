package com.entity;

import java.util.Date;

import com.annotation.JoeAnotation;

public class Man {
	@JoeAnotation(Id = "p_id", table = "tb_person")
	private Integer personId;

	@JoeAnotation(column = "p_name")
	
	private String personName;
	
	
	@JoeAnotation(column = "p_brithday")
	private Date brithday;

	public Date getBrithday() {
		return brithday;
	}

	public void setBrithday(Date brithday) {
		this.brithday = brithday;
	}

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
}
