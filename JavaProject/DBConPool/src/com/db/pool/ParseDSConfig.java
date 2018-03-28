package com.db.pool;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

@SuppressWarnings("unchecked")
public class ParseDSConfig {
	/**
	 * ��ȡxml�����ļ�
	 * 
	 * @param path
	 * @return
	 */
	public Vector<DSConfigBean> readConfigInfo(String path) {
		String rpath = this.getClass().getResource("").getPath().substring(1)
				+ path;
		Vector<DSConfigBean> dsConfig = null;
		FileInputStream fi = null;
		try {
			fi = new FileInputStream(rpath);// ��ȡ·���ļ�
			dsConfig = new Vector<DSConfigBean>();
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(fi);
			Element root = doc.getRootElement();
		
			List<Element> pools = root.getChildren();
			Element pool = null;
			Iterator<Element> allPool = pools.iterator();
			while (allPool.hasNext()) {
				pool = (Element) allPool.next();
				DSConfigBean dscBean = new DSConfigBean();
				dscBean.setType(pool.getChild("type").getText());
				dscBean.setName(pool.getChild("name").getText());
				System.out.println(dscBean.getName());
				dscBean.setDriver(pool.getChild("driver").getText());
				dscBean.setUrl(pool.getChild("url").getText());
				dscBean.setUsername(pool.getChild("username").getText());
				dscBean.setPassword(pool.getChild("password").getText());
				dscBean.setMaxconn(Integer.parseInt(pool.getChild("maxconn").getText()));
				dsConfig.add(dscBean);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		finally {
			try {
				fi.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return dsConfig;
	}

	/**
	 * �޸������ļ� ûʱ��д ����ʱ��������ȥ ��ʵһ����
	 */
	@SuppressWarnings("unused")
	public void modifyConfigInfo(String path, DSConfigBean dsb)
			throws Exception {
		
		String rpath = this.getClass().getResource("").getPath().substring(1)+ path;
		FileInputStream fi = null; // ����
		FileOutputStream fo = null; // д��

	}

	/**
	 * ���������ļ�
	 * 
	 */
	public void addConfigInfo(String path, DSConfigBean dsb) {
		String rpath = this.getClass().getResource("").getPath().substring(1)
				+ path;
		FileInputStream fi = null;
		FileOutputStream fo = null;
		try {
			fi = new FileInputStream(rpath);// ��ȡxml��

			SAXBuilder sb = new SAXBuilder();

			Document doc = sb.build(fi); // �õ�xml
			Element root = doc.getRootElement();
			
			List<Element> pools = root.getChildren();// �õ�xml����

			Element newpool = new Element("pool"); // ���������ӳ�

			Element pooltype = new Element("type"); // �������ӳ�����
			pooltype.setText(dsb.getType());
			newpool.addContent(pooltype);

			Element poolname = new Element("name");// �������ӳ�����
			poolname.setText(dsb.getName());
			newpool.addContent(poolname);

			Element pooldriver = new Element("driver"); // �������ӳ�����
			pooldriver.addContent(dsb.getDriver());
			newpool.addContent(pooldriver);

			Element poolurl = new Element("url");// �������ӳ�url
			poolurl.setText(dsb.getUrl());
			newpool.addContent(poolurl);

			Element poolusername = new Element("username");// �������ӳ��û���
			poolusername.setText(dsb.getUsername());
			newpool.addContent(poolusername);

			Element poolpassword = new Element("password");// �������ӳ�����
			poolpassword.setText(dsb.getPassword());
			newpool.addContent(poolpassword);

			Element poolmaxconn = new Element("maxconn");// �������ӳ��������
			poolmaxconn.setText(String.valueOf(dsb.getMaxconn()));
			newpool.addContent(poolmaxconn);
			pools.add(newpool);// ��child��ӵ�root
			Format format = Format.getPrettyFormat();
			format.setIndent("");
			format.setEncoding("utf-8");
			XMLOutputter outp = new XMLOutputter(format);
			fo = new FileOutputStream(rpath);
			outp.output(doc, fo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	/**
	 * ɾ�������ļ�
	 */
	public void delConfigInfo(String path, String name) {
		String rpath = this.getClass().getResource("").getPath().substring(1)
				+ path;
		FileInputStream fi = null;
		FileOutputStream fo = null;
		try {
			fi = new FileInputStream(rpath);// ��ȡ·���ļ�
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(fi);
			Element root = doc.getRootElement();
			List<Element> pools = root.getChildren();
			Element pool = null;
			Iterator<Element> allPool = pools.iterator();
			while (allPool.hasNext()) {
				pool = (Element) allPool.next();
				if (pool.getChild("name").getText().equals(name)) {
					pools.remove(pool);
					break;
				}
			}
			Format format = Format.getPrettyFormat();
			format.setIndent("");
			format.setEncoding("utf-8");
			XMLOutputter outp = new XMLOutputter(format);
			fo = new FileOutputStream(rpath);
			outp.output(doc, fo);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally {
			try {
				fi.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
