package com.readfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ReadFileDemo {
	/**
	 * �����·���µ��ļ�·��
	 * 
	 * @param filename
	 *            �ļ���
	 * @return �ļ�·��+�ļ��� ���� URL
	 */
	private static URL getPath(String filename) {
		return ReadFileDemo.class.getClassLoader().getResource(filename);
	}

	/**
	 * ���ֽ�����ʽ��ȡ�ļ�
	 * 
	 * @param filename
	 *            �ļ���
	 * @param endcode
	 *            �����ʽ
	 */
	public static void readByStream(String filename, String encode) {
		try {
			// ȥ��ǰ�� "file:/" �����ַ�6��
			File file = new File(getPath(filename).toString().substring(6));

			if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encode);// ���ǵ������ʽ
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					System.out.println(lineTxt);
					// �˴�Ϊ�ļ�������
				}
				read.close();
			} else {
				System.out.println("�Ҳ���ָ�����ļ�");
			}
		} catch (Exception e) {
			System.out.println("��ȡ�ļ����ݳ���");
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ �򵥸�ʽXML�ļ�
	 * 
	 * @param filename
	 *            �ļ���
	 */
	public static void readXMLSample(String filename) {
		URL xmlpath = getPath(filename);
		try {
			Document doc = new SAXReader().read(xmlpath);
			Element element = doc.getRootElement();

			String server = element.elementText("server");
			String port = element.elementText("port");

			System.out.println("XML����\n Server:" + server + " Port:" + port);
			// �˴�Ϊ�ļ�������

		} catch (DocumentException e) {
			System.out.println("�ļ���ȡʧ��");
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ ���Ӹ�ʽXML�ļ�
	 * 
	 * @param filename
	 *            �ļ���
	 */
	@SuppressWarnings("rawtypes")
	public static void readXMLComplex(String filename) {
		URL xmlpath = getPath(filename);
		try {
			// ʹ��SAX��ʽ����XML,��XML�ļ��������ĵ�����
			Document doc = new SAXReader().read(xmlpath);
			Element root = doc.getRootElement(); // ȡ�ø��ڵ�
			Iterator iterator = root.elementIterator("txtbook"); // �ӵڶ����ڵ㿪ʼ����

			while (iterator.hasNext()) {
				Element txtElement = (Element) iterator.next(); // ��ȡ�ڶ����ڵ�
				List list_element = (List) txtElement.elements(); // ���ڶ����ڵ���ӽڵ���뵽list��

				Iterator leaf_element = list_element.iterator(); // ��ʼ�����������ڵ�
				while (leaf_element.hasNext()) {
					Element leaf_data = (Element) leaf_element.next(); // ��ȡ�������ڵ�

					/* �жϵ������ڵ�������Ƿ�Ϊname ��:��ȡ�ı�ֵ��ʾ����:����ʾ */
					/*
					 * if (("name").equals(leaf_data.getName().toString())) {
					 * String name = leaf_data.getText();
					 * System.out.println("name�ڵ� ����:"+name); }
					 */

					String node_key = leaf_data.getName().toString();
					String node_value = leaf_data.getText();
					System.out.println(node_key + ":" + node_value);
					/* �˴�Ϊ�ļ����ݲ����� */
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

}
