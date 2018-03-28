package com.junit;

import org.junit.Test;

import com.proxy.JDKProxyFactory;
import com.proxy.CGlibProxyFactory;
import com.service.PersonService;
import com.service.impl.PersonServiceImpl;

public class TestProxy {

	@Test
	/* �������Ľӿڴ���*/
	public void JDKProxyFactory() {
		PersonService personService = (PersonService) new JDKProxyFactory().createProxyIntance(new Object(),new PersonServiceImpl("cc"));
		personService.save("����");
		personService.update("С��", 1);
	}

	@Test
	/* ���������������*/
	public void CGlibProxyFactory() {
		PersonServiceImpl personService = (PersonServiceImpl) new CGlibProxyFactory()
				.createProxyFactory(new PersonServiceImpl());
		personService.save("Joe");
		personService.update("Luce", 2);

	}

}
