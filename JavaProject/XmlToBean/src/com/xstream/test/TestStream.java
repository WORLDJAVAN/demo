package com.xstream.test;

import com.thoughtworks.xstream.XStream;
import com.xstream.bean.Address;
import com.xstream.bean.Person;

public class TestStream {

	public static void main(String[] args) {
		/* ����ʵ�� PersonBean ���� */
		Person person = createBean();
		
		/* ��ʵ����ȡXML���� */
		String tagXML = BeanToXML(person);
		
		/* ��XML����ת���� PersonBean ���� */
		XmlToBean(tagXML);
	}

	public static void XmlToBean(String in_XML) {
		XStream xs = new XStream();
		xs.setMode(XStream.NO_REFERENCES);
		
		/* ����ʹ��ע��VOģʽ */
		xs.processAnnotations(new Class[] { Person.class, Address.class });

		Person person = (Person) xs.fromXML(in_XML);

		System.out.println("���ƺţ�"+person.getAddresses().get(0).getHouseNo()
				+ " ס����"+person.getName());
	}

	public static String BeanToXML(Person person) {
			
		/* ���� XStream ���� */
		XStream xStream = new XStream();
		xStream.setMode(XStream.NO_REFERENCES);
		
		/* ����ʹ��ע��VOģʽ */
		xStream.processAnnotations(new Class[] { Person.class, Address.class });

       /* �ô������XML�ڵ���Ϣ,���ȼ�����ע��ģʽ, ��ϸ�÷���Person����˵��		
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
		/* ����XML��ʽ���� */
		Person person = new Person();
		person.setName("zoulong");
		person.setPhoneNuber(1387356884);
		Address address1 = new Address();
		address1.setHouseNo(88);
		address1.setStreet("ŦԼ����˹ɳ�ֽ�");
		Address address2 = new Address();
		address2.setHouseNo(66);
		address2.setStreet("����˹÷�����");
		person.getAddresses().add(address1);
		person.getAddresses().add(address2);

		return person;
	}
}
