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
	 * 获得类路径下的文件路径
	 * 
	 * @param filename
	 *            文件名
	 * @return 文件路径+文件名 类型 URL
	 */
	private static URL getPath(String filename) {
		return ReadFileDemo.class.getClassLoader().getResource(filename);
	}

	/**
	 * 以字节流方式读取文件
	 * 
	 * @param filename
	 *            文件名
	 * @param endcode
	 *            编码格式
	 */
	public static void readByStream(String filename, String encode) {
		try {
			// 去掉前面 "file:/" 无用字符6个
			File file = new File(getPath(filename).toString().substring(6));

			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encode);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					System.out.println(lineTxt);
					// 此处为文件操作区
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
	}

	/**
	 * 读取 简单格式XML文件
	 * 
	 * @param filename
	 *            文件名
	 */
	public static void readXMLSample(String filename) {
		URL xmlpath = getPath(filename);
		try {
			Document doc = new SAXReader().read(xmlpath);
			Element element = doc.getRootElement();

			String server = element.elementText("server");
			String port = element.elementText("port");

			System.out.println("XML内容\n Server:" + server + " Port:" + port);
			// 此处为文件操作区

		} catch (DocumentException e) {
			System.out.println("文件读取失败");
			e.printStackTrace();
		}
	}

	/**
	 * 读取 复杂格式XML文件
	 * 
	 * @param filename
	 *            文件名
	 */
	@SuppressWarnings("rawtypes")
	public static void readXMLComplex(String filename) {
		URL xmlpath = getPath(filename);
		try {
			// 使用SAX方式解析XML,将XML文件解析成文档对象
			Document doc = new SAXReader().read(xmlpath);
			Element root = doc.getRootElement(); // 取得根节点
			Iterator iterator = root.elementIterator("txtbook"); // 从第二级节点开始遍历

			while (iterator.hasNext()) {
				Element txtElement = (Element) iterator.next(); // 获取第二级节点
				List list_element = (List) txtElement.elements(); // 将第二级节点的子节点放入到list中

				Iterator leaf_element = list_element.iterator(); // 开始遍历第三级节点
				while (leaf_element.hasNext()) {
					Element leaf_data = (Element) leaf_element.next(); // 获取第三级节点

					/* 判断第三级节点的名称是否为name 是:获取文本值显示、否:不显示 */
					/*
					 * if (("name").equals(leaf_data.getName().toString())) {
					 * String name = leaf_data.getText();
					 * System.out.println("name节点 书名:"+name); }
					 */

					String node_key = leaf_data.getName().toString();
					String node_value = leaf_data.getText();
					System.out.println(node_key + ":" + node_value);
					/* 此处为文件数据操作区 */
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

}
