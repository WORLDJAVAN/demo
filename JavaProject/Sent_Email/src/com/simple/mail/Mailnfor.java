package com.simple.mail;

import javax.mail.*;

public class Mailnfor extends Authenticator {
	
	String userName = null;
	String password = null;

	public Mailnfor() {
	}

	public Mailnfor(String username, String password) {
		this.userName = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}
}
