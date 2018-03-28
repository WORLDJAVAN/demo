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
	
	/*1��Connection�Ļ�ú��ͷ�
	  DBConnectionManager   connectionMan=DBConnectionManager .getInstance();//�õ�Ψһʵ��
	   //�õ�����
	   String name="mysql";//�������ĵõ���Ҫ���ʵ����ݿ������
	   Connection  con=connectionMan.getConnection(name);
	  //ʹ��
	  ��������������
	  // ʹ�����
	 connectionMan.freeConnection(name,con);//�ͷţ�����δ�Ͽ�����
*/
}
