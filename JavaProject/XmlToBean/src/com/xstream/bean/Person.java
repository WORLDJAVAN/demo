package com.xstream.bean;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/*
 * @XStreamAlias 表示对应在XML中结点名称
 * 效果等同于 xStream.alias("person", Person.class);
 */
@XStreamAlias("person") 
public class Person {
	
	@XStreamAsAttribute()
	/*
	 * 使name属性封装到根结点中  如：<person name="xxx">
	 * 效果等同 xStream.useAttributeFor(Person.class,"name")
	 */
	private String name;
	
	@XStreamAlias("phoneNo") 
	/*
	 * 使phoneNuber属性封装到XML节点名称  如：<phoneNo>
	 * 效果等同 xStream.aliasField("phNmber", Person.class, "phoneNuber");
	 */
	private int phoneNuber;
	
	/*
	 * 使 addresses 属性不封装到 XML结构中
	 * 效果等同 xStream.addImplicitCollection(Person.class, "addresses")
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
