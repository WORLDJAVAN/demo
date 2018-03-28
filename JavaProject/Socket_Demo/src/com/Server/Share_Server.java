package com.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * 支持多客户端服务连接，
 * 动态创建处理连接线程
 */
public class Share_Server extends ServerSocket {
	// 默认监听端口
	private static final int SERVER_PORT = 10000;
	
    /*
     * 主接收客户端函数
     */
	public Share_Server() throws IOException {
		super(SERVER_PORT);
		try {
			// 创建可以同时多个客户端的连接
			while (true) {
				// 接收到客户端连接的Socket变量
				Socket socket = accept();
				new CreateServerThread(socket);
			}
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			close();
		}
	}

	/*
	 * 创建内部类的处理线程，用于接收客户端的多个Socket连接
	 */
	class CreateServerThread extends Thread {
		private Socket client;
		private BufferedReader in;
		private PrintWriter out;

		public CreateServerThread(Socket socket) {
			client = socket;
			try {
				in = new BufferedReader(new InputStreamReader(
						client.getInputStream(), "GB2312"));
				out = new PrintWriter(client.getOutputStream(), true);
				out.println("--- Welcome ---");
				
				// 执行线程的run()方法,处理信息交互
				start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
	
		public void run() {
			try {
				String line = in.readLine();
				while (!line.equals("bye")) {
					String msg = createMsg(line);
					out.println(msg);
					line = in.readLine();
				}
				out.println("--- See you, bye! ---");
				client.close();
			} catch (IOException e) {
			}
		}
		
		public String createMsg(String message){
			// 处理读取的信息
			return message+"create_server";
		}

	} // class CreateServerThread
	
   /*
    * 主调试函数main()
    */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        try {
			new Share_Server();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
