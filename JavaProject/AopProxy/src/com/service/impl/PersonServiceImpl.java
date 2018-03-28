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
		System.out.println("我 是save方法,你保存的名字是:"+name);
	}

	@Override
	public void update(String name, Integer personid) {
		System.out.println("我 是update方法");
	}

	@Override
	public String getPersonName(Integer personid) {
		return "xxxx";
	}
}
