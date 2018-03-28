package com.joeload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyClassLoader extends ClassLoader {
	// 类加载器名称
	private String name;
	// 加载类的路径
	private String path = "D:/Trains/JavaProject/Dynamic-Class/classes/";
	private final String fileType = ".class";

	public MyClassLoader() {
		// 让系统类加载器成为该 类加载器的父加载器
		super();
	}
	
	public MyClassLoader(String name) {
		// 让系统类加载器成为该 类加载器的父加载器
		super();
		this.name = name;
	}

	/**
	 * 获取.class文件的字节数组
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
	 * 获取Class对象
	 */
	@Override
	public Class<?> findClass(String name) {
		byte[] data = loaderClassData(name);
		return this.defineClass(name, data, 0, data.length);
	}
}
