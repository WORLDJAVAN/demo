package com.pool.test;

import com.db.pool.DSConfigBean;
import com.db.pool.ParseDSConfig;

public class TestConPool {

	public static void main(String[] args) {
		 ParseDSConfig pd=new ParseDSConfig();
		  String path="ds.config.xml";
		  pd.readConfigInfo(path);
		  //pd.delConfigInfo(path, "tj012006");
		  DSConfigBean dsb=new DSConfigBean();
		  dsb.setType("oracle");
		  dsb.setName("yyy004");
		  dsb.setDriver("org.oracle.jdbc");
		  dsb.setUrl("jdbc:oracle://localhost");
		  dsb.setUsername("sa");
		  dsb.setPassword("");
		  dsb.setMaxconn(1000);
		  pd.addConfigInfo(path, dsb);
		  pd.delConfigInfo(path, "yyy001");
	}
	
	/*1。Connection的获得和释放
	  DBConnectionManager   connectionMan=DBConnectionManager .getInstance();//得到唯一实例
	   //得到连接
	   String name="mysql";//从上下文得到你要访问的数据库的名字
	   Connection  con=connectionMan.getConnection(name);
	  //使用
	  。。。。。。。
	  // 使用完毕
	 connectionMan.freeConnection(name,con);//释放，但并未断开连接
*/
}
