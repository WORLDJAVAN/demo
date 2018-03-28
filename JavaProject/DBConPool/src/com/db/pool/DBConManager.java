package com.db.pool;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public class DBConManager {
	static private DBConManager instance;// 唯一数据库连接池管理实例类
	//static private int clients; // 客户连接数
	@SuppressWarnings("rawtypes")
	private Vector drivers = new Vector();// 驱动信息
	@SuppressWarnings("rawtypes")
	private Hashtable pools = new Hashtable();// 连接池

	/**
	 * 实例化管理类
	 */
	public DBConManager() {
		this.init();
	}

	/**
	 * 得到唯一实例管理类
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
	 * 释放连接
	 * 
	 * @param name
	 * @param con
	 */
	public void freeConnection(String name, Connection con) {
		DBConPool pool = (DBConPool) pools.get(name);// 根据关键名字得到连接池
		if (pool != null)
			pool.freeConnection(con);// 释放连接
	}

	/**
	 * 得到一个连接根据连接池的名字name
	 * 
	 * @param name
	 * @return
	 */
	public Connection getConnection(String name) {
		DBConPool pool = null;
		Connection con = null;
		pool = (DBConPool) pools.get(name);// 从名字中获取连接池
		con = pool.getConnection();// 从选定的连接池中获得连接
		if (con != null)
			System.out.println("得到连接。。。");
		return con;
	}

	/**
	 * 得到一个连接，根据连接池的名字和等待时间
	 * 
	 * @param name
	 * @param time
	 * @return
	 */
	public Connection getConnection(String name, long timeout) {
		DBConPool pool = null;
		Connection con = null;
		pool = (DBConPool) pools.get(name);// 从名字中获取连接池
		con = pool.getConnection(timeout);// 从选定的连接池中获得连接
		System.out.println("得到连接。。。");
		return con;
	}

	/**
	 * 释放所有连接
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
	 * 创建连接池
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
	 * 初始化连接池的参数
	 */
	private void init() {
		// 加载驱动程序
		this.loadDrivers();
		// 创建连接池
		@SuppressWarnings("rawtypes")
		Iterator alldriver = drivers.iterator();
		while (alldriver.hasNext()) {
			this.createPools((DSConfigBean) alldriver.next());
			System.out.println("创建连接池。。。");

		}
		System.out.println("创建连接池完毕。。。");
	}

	/**
	 * 加载驱动程序
	 * 
	 * @param props
	 */
	private void loadDrivers() {
		ParseDSConfig pd = new ParseDSConfig();
		// 读取数据库配置文件
		drivers =  pd.readConfigInfo("ds.config.xml");
		System.out.println("加载驱动程序。。。");
	}
}
