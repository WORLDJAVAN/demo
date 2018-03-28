package com.db.pool;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public class DBConManager {
	static private DBConManager instance;// Ψһ���ݿ����ӳع���ʵ����
	//static private int clients; // �ͻ�������
	@SuppressWarnings("rawtypes")
	private Vector drivers = new Vector();// ������Ϣ
	@SuppressWarnings("rawtypes")
	private Hashtable pools = new Hashtable();// ���ӳ�

	/**
	 * ʵ����������
	 */
	public DBConManager() {
		this.init();
	}

	/**
	 * �õ�Ψһʵ��������
	 * 
	 * @return
	 */
	static synchronized public DBConManager getInstance() {
		if (instance == null) {
			instance = new DBConManager();
		}
		return instance;
	}

	/**
	 * �ͷ�����
	 * 
	 * @param name
	 * @param con
	 */
	public void freeConnection(String name, Connection con) {
		DBConPool pool = (DBConPool) pools.get(name);// ���ݹؼ����ֵõ����ӳ�
		if (pool != null)
			pool.freeConnection(con);// �ͷ�����
	}

	/**
	 * �õ�һ�����Ӹ������ӳص�����name
	 * 
	 * @param name
	 * @return
	 */
	public Connection getConnection(String name) {
		DBConPool pool = null;
		Connection con = null;
		pool = (DBConPool) pools.get(name);// �������л�ȡ���ӳ�
		con = pool.getConnection();// ��ѡ�������ӳ��л������
		if (con != null)
			System.out.println("�õ����ӡ�����");
		return con;
	}

	/**
	 * �õ�һ�����ӣ��������ӳص����ֺ͵ȴ�ʱ��
	 * 
	 * @param name
	 * @param time
	 * @return
	 */
	public Connection getConnection(String name, long timeout) {
		DBConPool pool = null;
		Connection con = null;
		pool = (DBConPool) pools.get(name);// �������л�ȡ���ӳ�
		con = pool.getConnection(timeout);// ��ѡ�������ӳ��л������
		System.out.println("�õ����ӡ�����");
		return con;
	}

	/**
	 * �ͷ���������
	 */
	public synchronized void release() {
		@SuppressWarnings("rawtypes")
		Enumeration allpools = pools.elements();
		while (allpools.hasMoreElements()) {
			DBConPool pool = (DBConPool) allpools.nextElement();
			if (pool != null)
				pool.release();
		}
		pools.clear();
	}

	/**
	 * �������ӳ�
	 * 
	 * @param props
	 */
	@SuppressWarnings("unchecked")
	private void createPools(DSConfigBean dscBean) {
		DBConPool dbpool = new DBConPool();
		dbpool.setName(dscBean.getName());
		dbpool.setDriver(dscBean.getDriver());
		dbpool.setUrl(dscBean.getUrl());
		dbpool.setUser(dscBean.getUsername());
		dbpool.setPassword(dscBean.getPassword());
		dbpool.setMaxConn(dscBean.getMaxconn());
		System.out.println("ioio:" + dscBean.getMaxconn());
		pools.put(dscBean.getName(), dbpool);
	}

	/**
	 * ��ʼ�����ӳصĲ���
	 */
	private void init() {
		// ������������
		this.loadDrivers();
		// �������ӳ�
		@SuppressWarnings("rawtypes")
		Iterator alldriver = drivers.iterator();
		while (alldriver.hasNext()) {
			this.createPools((DSConfigBean) alldriver.next());
			System.out.println("�������ӳء�����");

		}
		System.out.println("�������ӳ���ϡ�����");
	}

	/**
	 * ������������
	 * 
	 * @param props
	 */
	private void loadDrivers() {
		ParseDSConfig pd = new ParseDSConfig();
		// ��ȡ���ݿ������ļ�
		drivers =  pd.readConfigInfo("ds.config.xml");
		System.out.println("�����������򡣡���");
	}
}
