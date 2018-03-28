package junit.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.service.PersonService;

public class SpringTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void instanceSpring() {
       /*
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		PersonService personService = (PersonService) ctx.getBean("personService");
		personService.save();*/

		
		 /* ÈÝÆ÷²âÊÔ	*/ 
		  ItcastClassPathXMLApplicationContext ctx = new ItcastClassPathXMLApplicationContext("beans.xml"); 
		  PersonService personService = (PersonService)ctx.getBean("personService");
		 // personService.save();	
		  System.out.println(personService);
	    		 
	}
}
