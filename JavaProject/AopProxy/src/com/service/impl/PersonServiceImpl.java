package com.service.impl;

import com.service.PersonService;

public class PersonServiceImpl implements PersonService {
	private String user = null;

	public String getUser() {
		return user;
	}

	public PersonServiceImpl() {
	}

	public PersonServiceImpl(String user) {
		this.user = user;
	}

	@Override
	public void save(String name) {
		System.out.println("�� ��save����,�㱣���������:"+name);
	}

	@Override
	public void update(String name, Integer personid) {
		System.out.println("�� ��update����");
	}

	@Override
	public String getPersonName(Integer personid) {
		return "xxxx";
	}
}
