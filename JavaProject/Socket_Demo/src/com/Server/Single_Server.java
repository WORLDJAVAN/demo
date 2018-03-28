package com.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * 建立连接后给客户端返回一段信息，然后结束会话。
 * 这个程序一次只能接受一个客户连接
 */
public class Single_Server {
	private ServerSocket socket_server;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	/**
	 * 初始服务器设置
	 */
	public Single_Server() {
		try {
			// 创建一个SocketServer对象
			socket_server = new ServerSocket(10000);
			Boolean conditon = true;
			while (conditon) {
				// 创建一个socket及 与客户端的socket建立信息通道
				socket = socket_server.accept();
				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);

				// 进行信息通信
				String line = in.readLine();
				out.println("you input is :" + line);

				// 关闭信息连接
				if (line == "end") {
					conditon = false;
					out.close();
					in.close();
					socket.close();
				}
			}
			socket_server.close();

		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		new Single_Server();
	}
}
