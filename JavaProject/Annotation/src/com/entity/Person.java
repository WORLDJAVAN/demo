package com.entity;

import com.annotation.JoeField;

public class Person {
	@JoeField(field = "p_name")
	private String personName;
	@JoeField(field = "p_Id")
	private Integer personId;
	@JoeField(field = "p_IDcard")
	private String personIDcard;

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getPersonIDcard() {
		return personIDcard;
	}

	public void setPersonIDcard(String personIDcard) {
		this.personIDcard = personIDcard;
	}
}
