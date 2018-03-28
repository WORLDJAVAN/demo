package com.xstream.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/*
 * @XStreamAlias ��ʾ��Ӧ��XML�н������
 */
@XStreamAlias("address")  
public class Address {
	private String street;
	private int houseNo;
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public int getHouseNo() {
		return houseNo;
	}
	public void setHouseNo(int houseNo) {
		this.houseNo = houseNo;
	}
}
