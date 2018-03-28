package com.db.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import commonj.timers.*;

public class DBConPool implements TimerListener {
	private Connection con = null;
	private int inUsed = 0; // 使用的连接数
	private ArrayList<Connection> freeCon = new ArrayList<Connection>();// 容器，空闲连接
	private int minConn; // 最小连接数
	private int maxConn; // 最大连接
	private String name; // 连接池名字
	private String password; // 密码
	private String url; // 数据库连接地址
	private String driver; // 驱动
	private String user; // 用户名
	public Timer timer; // 定时

	@Override
	public void timerExpired(Timer time) {
		try {
			/* 检查连接数有效性 */
			for (Connection con : freeCon)
				if (con.isClosed()) {
					freeCon.remove(con);
				}
			
			int activeCon = freeCon.size() + inUsed;
			
			if (activeCon < minConn) {
				for (int count = 0; count < minConn - activeCon; count++) {
					freeCon.add(newConnection());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 申明默认构造
	 */
	public DBConPool() {
	}

	/**
	 * 创建连接池
	 * 
	 * @param driver
	 * @param name
	 * @param URL
	 * @param user
	 * @param password
	 * @param maxConn
	 */
	public DBConPool(String name, String driver, String URL,
			String user, String password, int maxConn, int minConn) {
		this.name = name;
		this.driver = driver;
		this.url = URL;
		this.user = user;
		this.password = password;
		this.maxConn = maxConn;
		this.minConn = minConn;
	}

	public String getDriver() {
		return driver;
	}

	/**
	 * @param driver
	 *            the driver to set
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}

	/**
	 * @return the maxConn
	 */
	public int getMaxConn() {
		return maxConn;
	}

	/**
	 * @param maxConn
	 *            the maxConn to set
	 */
	public void setMaxConn(int maxConn) {
		this.maxConn = maxConn;
	}

	/**
	 * @return the minConn
	 */
	public int getMinConn() {
		return minConn;
	}

	/**
	 * @param minConn the minConn to set
	 */
	public void setMinConn(int minConn) {
		this.minConn = minConn;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *  the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * 用完，释放连接
	 * 
	 * @param con
	 */
	public synchronized void freeConnection(Connection con) {
		this.freeCon.add(con);// 添加到空闲连接的末尾
		this.inUsed--;
	}

	/**
	 * timeout 根据timeout得到连接
	 * 
	 * @param timeout
	 * @return
	 */
	public synchronized Connection getConnection(long timeout) {
		Connection con = null;
		if (this.freeCon.size() > 0) {
			con = (Connection) this.freeCon.get(0);
			if (con == null)
				con = getConnection(timeout); // 继续获得连接
		} else {
			con = newConnection(); // 新建连接
		}
		if (this.maxConn == 0 || this.maxConn < this.inUsed) {
			con = null;// 达到最大连接数，暂时不能获得连接了。
		}
		if (con != null) {
			this.inUsed++;
		}
		return con;
	}

	/**
	 * 
	 * 从连接池里得到连接
	 * 
	 * @return
	 */
	public synchronized Connection getConnection() {
		Connection con = null;
		if (this.freeCon.size() > 0) {
			con = (Connection) this.freeCon.get(0);
			this.freeCon.remove(0);// 如果连接分配出去了，就从空闲连接里删除
			if (con == null)
				con = getConnection(); // 继续获得连接
		} else {
			con = newConnection(); // 新建连接
		}
		if (this.maxConn == 0 || this.maxConn < this.inUsed) {
			con = null;// 等待 超过最大连接时
		}
		if (con != null) {
			this.inUsed++;
			System.out.println("得到　" + this.name + "　的连接，现有" + inUsed
					+ "个连接在使用!");
		}
		return con;
	}

	/**
	 * 释放全部连接
	 * 
	 */
	public synchronized void release() {
		@SuppressWarnings("rawtypes")
		Iterator allConns = this.freeCon.iterator();
		while (allConns.hasNext()) {
			Connection con = (Connection) allConns.next();
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		this.freeCon.clear();

	}

	/**
	 * 创建新连接
	 * 
	 * @return
	 */
	private Connection newConnection() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("sorry can't find db driver!");
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("sorry can't create Connection!");
		}
		return con;
	}
}
