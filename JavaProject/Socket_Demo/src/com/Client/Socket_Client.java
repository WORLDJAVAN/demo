package com.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Socket_Client {
	Socket socket;
	BufferedReader in;
	PrintWriter out;

	public static void main(String[] args) {
		 new Socket_Client();
	}

	public Socket_Client() {
		try {
			// 创建一个Socket对象
			socket = new Socket("localhost", 10000);
			// 建立起 输入、输出通道
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			
			// 建立需要传输的数据信息
			BufferedReader line = new BufferedReader(new InputStreamReader(
					System.in));		
			out.println(line.readLine());
			line.close();
			
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
