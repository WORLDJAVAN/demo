package com.entity;

public class ParamEntity {
	private String IP; // �ͻ��������IP
	private String password; // �û���Ҫ��֤������
	private String accountId; // �����û����ʺ���Ϣ
	private int suit; // �û��ļ���
	private int costmoney; // ���ڻ��ֵ����ѽ��
	private int changemoney; // ��Ҫ���ֶһ��Ľ��
	private String moneyType; // ���ڼ�¼��������, 0:��ֵ 1:��������
	private String urlType; // ���ڼ�¼��������

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public int getSuit() {
		return suit;
	}

	public void setSuit(int suit) {
		this.suit = suit;
	}

	public int getCostmoney() {
		return costmoney;
	}

	public void setCostmoney(int costmoney) {
		this.costmoney = costmoney;
	}

	public int getChangemoney() {
		return changemoney;
	}

	public void setChangemoney(int changemoney) {
		this.changemoney = changemoney;
	}

	public String getMoneyType() {
		return moneyType;
	}

	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}

	public String getUrlType() {
		return urlType;
	}

	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}
}
