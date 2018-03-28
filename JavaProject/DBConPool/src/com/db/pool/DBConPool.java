package com.db.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import commonj.timers.*;

public class DBConPool implements TimerListener {
	private Connection con = null;
	private int inUsed = 0; // ʹ�õ�������
	private ArrayList<Connection> freeCon = new ArrayList<Connection>();// ��������������
	private int minConn; // ��С������
	private int maxConn; // �������
	private String name; // ���ӳ�����
	private String password; // ����
	private String url; // ���ݿ����ӵ�ַ
	private String driver; // ����
	private String user; // �û���
	public Timer timer; // ��ʱ

	@Override
	public void timerExpired(Timer time) {
		try {
			/* �����������Ч�� */
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
	 * ����Ĭ�Ϲ���
	 */
	public DBConPool() {
	}

	/**
	 * �������ӳ�
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
	 * ���꣬�ͷ�����
	 * 
	 * @param con
	 */
	public synchronized void freeConnection(Connection con) {
		this.freeCon.add(con);// ��ӵ��������ӵ�ĩβ
		this.inUsed--;
	}

	/**
	 * timeout ����timeout�õ�����
	 * 
	 * @param timeout
	 * @return
	 */
	public synchronized Connection getConnection(long timeout) {
		Connection con = null;
		if (this.freeCon.size() > 0) {
			con = (Connection) this.freeCon.get(0);
			if (con == null)
				con = getConnection(timeout); // �����������
		} else {
			con = newConnection(); // �½�����
		}
		if (this.maxConn == 0 || this.maxConn < this.inUsed) {
			con = null;// �ﵽ�������������ʱ���ܻ�������ˡ�
		}
		if (con != null) {
			this.inUsed++;
		}
		return con;
	}

	/**
	 * 
	 * �����ӳ���õ�����
	 * 
	 * @return
	 */
	public synchronized Connection getConnection() {
		Connection con = null;
		if (this.freeCon.size() > 0) {
			con = (Connection) this.freeCon.get(0);
			this.freeCon.remove(0);// ������ӷ����ȥ�ˣ��ʹӿ���������ɾ��
			if (con == null)
				con = getConnection(); // �����������
		} else {
			con = newConnection(); // �½�����
		}
		if (this.maxConn == 0 || this.maxConn < this.inUsed) {
			con = null;// �ȴ� �����������ʱ
		}
		if (con != null) {
			this.inUsed++;
			System.out.println("�õ���" + this.name + "�������ӣ�����" + inUsed
					+ "��������ʹ��!");
		}
		return con;
	}

	/**
	 * �ͷ�ȫ������
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
	 * ����������
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
