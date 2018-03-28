package com.entity;

public class ParamEntity {
	private String IP; // 客户端请求的IP
	private String password; // 用户需要验证的密码
	private String accountId; // 操作用户的帐号信息
	private int suit; // 用户寄件数
	private int costmoney; // 用于积分的消费金额
	private int changemoney; // 需要积分兑换的金额
	private String moneyType; // 用于记录消费类型, 0:充值 1:其他消费
	private String urlType; // 用于记录需求类型

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
