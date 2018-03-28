package com.readfile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProp {
	private static String serverHost = null;
	private static String serverPort = null;
	private static String userEmail = null;
	private static String password = null;

	static {
		Properties prop = new Properties();
		InputStream in = Object.class.getResourceAsStream("/prop.properties");
		try {
			prop.load(in);
			serverHost = prop.getProperty("serverHost").trim();
			serverPort = prop.getProperty("serverPort").trim();
			userEmail = prop.getProperty("userEmail").trim();
			password = prop.getProperty("password").trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getServerHost() {
		return serverHost;
	}

	public static String getServerPort() {
		return serverPort;
	}

	public static String getUserEmail() {
		return userEmail;
	}

	public static String getPassword() {
		return password;
	}
}
