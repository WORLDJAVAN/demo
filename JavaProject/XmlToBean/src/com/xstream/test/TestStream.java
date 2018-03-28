package com.xstream.test;

import com.thoughtworks.xstream.XStream;
import com.xstream.bean.Address;
import com.xstream.bean.Person;

public class TestStream {

	public static void main(String[] args) {
		/* 创建实例 PersonBean 对象 */
		Person person = createBean();
		
		/* 由实例获取XML对象 */
		String tagXML = BeanToXML(person);
		
		/* 由XML对象转换成 PersonBean 对象 */
		XmlToBean(tagXML);
	}

	public static void XmlToBean(String in_XML) {
		XStream xs = new XStream();
		xs.setMode(XStream.NO_REFERENCES);
		
		/* 开启使用注解VO模式 */
		xs.processAnnotations(new Class[] { Person.class, Address.class });

		Person person = (Person) xs.fromXML(in_XML);

		System.out.println("门牌号："+person.getAddresses().get(0).getHouseNo()
				+ " 住户："+person.getName());
	}

	public static String BeanToXML(Person person) {
			
		/* 设置 XStream 参数 */
		XStream xStream = new XStream();
		xStream.setMode(XStream.NO_REFERENCES);
		
		/* 开启使用注解VO模式 */
		xStream.processAnnotations(new Class[] { Person.class, Address.class });

       /* 用代码控制XML节点信息,优先级低于注解模式, 详细用法见Person类中说明		
        xStream.alias("person", Person.class);
		xStream.addImplicitCollection(Person.class, "addresses");
		xStream.useAttributeFor(Person.class, "name");
		xStream.aliasField("new_name", Person.class, "name");		
		*/
		
		String xml = xStream.toXML(person);

		System.out.println(xml);
		return xml;
	}
	
	public static Person createBean() {
		/* 设置XML格式内容 */
		Person person = new Person();
		person.setName("zoulong");
		person.setPhoneNuber(1387356884);
		Address address1 = new Address();
		address1.setHouseNo(88);
		address1.setStreet("纽约普林斯沙林街");
		Address address2 = new Address();
		address2.setHouseNo(66);
		address2.setStreet("格林斯梅西大街");
		person.getAddresses().add(address1);
		person.getAddresses().add(address2);

		return person;
	}
}
