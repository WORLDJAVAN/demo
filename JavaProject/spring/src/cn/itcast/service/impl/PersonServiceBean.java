package cn.itcast.service.impl;

import javax.annotation.Resource;

import cn.itcast.dao.PersonDao;
import cn.itcast.service.PersonService;

public class PersonServiceBean implements PersonService {	
	private PersonDao personDao;  

	public PersonDao getPersonDao() {
		return personDao;
	}
	
	@Resource
	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	public PersonServiceBean(){}  
	
	public PersonServiceBean(PersonDao personDao) {	
		this.personDao = personDao;
	}
	
	public void save() {		
		personDao.add(); // 接口方法调用
	}

}
