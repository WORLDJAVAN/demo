package com.xstream.bean;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/*
 * @XStreamAlias ��ʾ��Ӧ��XML�н������
 * Ч����ͬ�� xStream.alias("person", Person.class);
 */
@XStreamAlias("person") 
public class Person {
	
	@XStreamAsAttribute()
	/*
	 * ʹname���Է�װ���������  �磺<person name="xxx">
	 * Ч����ͬ xStream.useAttributeFor(Person.class,"name")
	 */
	private String name;
	
	@XStreamAlias("phoneNo") 
	/*
	 * ʹphoneNuber���Է�װ��XML�ڵ�����  �磺<phoneNo>
	 * Ч����ͬ xStream.aliasField("phNmber", Person.class, "phoneNuber");
	 */
	private int phoneNuber;
	
	/*
	 * ʹ addresses ���Բ���װ�� XML�ṹ��
	 * Ч����ͬ xStream.addImplicitCollection(Person.class, "addresses")
	 */
	 @XStreamImplicit()
	private List<Address> addresses = new ArrayList<Address>();

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPhoneNuber() {
		return phoneNuber;
	}

	public void setPhoneNuber(int phoneNuber) {
		this.phoneNuber = phoneNuber;
	}

}
