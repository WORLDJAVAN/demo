package com.junit;

import org.junit.Test;

import com.proxy.JDKProxyFactory;
import com.proxy.CGlibProxyFactory;
import com.service.PersonService;
import com.service.impl.PersonServiceImpl;

public class TestProxy {

	@Test
	/* 代理对像的接口代理*/
	public void JDKProxyFactory() {
		PersonService personService = (PersonService) new JDKProxyFactory().createProxyIntance(new Object(),new PersonServiceImpl("cc"));
		personService.save("张明");
		personService.update("小李", 1);
	}

	@Test
	/* 代理对像的子类代理*/
	public void CGlibProxyFactory() {
		PersonServiceImpl personService = (PersonServiceImpl) new CGlibProxyFactory()
				.createProxyFactory(new PersonServiceImpl());
		personService.save("Joe");
		personService.update("Luce", 2);

	}

}
