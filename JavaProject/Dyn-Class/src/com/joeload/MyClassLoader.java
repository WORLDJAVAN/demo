package com.joeload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyClassLoader extends ClassLoader {
	// �����������
	private String name;
	// �������·��
	private String path = "D:/Trains/JavaProject/Dynamic-Class/classes/";
	private final String fileType = ".class";

	public MyClassLoader() {
		// ��ϵͳ���������Ϊ�� ��������ĸ�������
		super();
	}
	
	public MyClassLoader(String name) {
		// ��ϵͳ���������Ϊ�� ��������ĸ�������
		super();
		this.name = name;
	}

	/**
	 * ��ȡ.class�ļ����ֽ�����
	 * 
	 * @param name
	 * @return
	 */
	private byte[] loaderClassData(String name) {
		InputStream is = null;
		byte[] data = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		this.name = this.name.replace(".", "/");

		try {

			is = new FileInputStream(new File(path + name + fileType));

			int c = 0;
			while (-1 != (c = is.read())) {
				baos.write(c);
			}
			data = baos.toByteArray();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	/**
	 * ��ȡClass����
	 */
	@Override
	public Class<?> findClass(String name) {
		byte[] data = loaderClassData(name);
		return this.defineClass(name, data, 0, data.length);
	}
}
