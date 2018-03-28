package com.Server;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

/*
 * 实现信息共享:在Socket上的实时交流
 * 多用户登陆并且输入信息后的屏幕。
 * 实现了信息的实时广播。
 * 用户输入"l"就可以列出在线人员表
 */
public class Mutual_Server extends ServerSocket {
	// 记录登陆用户列表
	private static ArrayList<String> User_List = new ArrayList<String>();
	// 记录登陆用户的处理socket处理线程信息
	private static ArrayList<CreateServerThread> Threader = new ArrayList<CreateServerThread>();
	// 记录客户端发送来的消息列表
	private static LinkedList<String> Message_Array = new LinkedList<String>();
	private static boolean isClear = true;
	protected static final int SERVER_PORT = 10000;
	// 记录连接日志信息
	protected FileOutputStream LOG_FILE = new FileOutputStream(
			"d:/connect.log", true);

	public Mutual_Server() throws IOException {
		super(SERVER_PORT);

		// 实现广播效果，记录、更新客户端信息
		new Broadcast();

		// 添加连接日志信息
		Calendar now = Calendar.getInstance();
		String str = "[" + now.getTime().toString()
				+ "] Accepted a connection1512";
		byte[] tmp = str.getBytes();
		LOG_FILE.write(tmp);
		
		// 创建用于接受客户端口的Socket对象
		try {
			while (true) {
				Socket socket = accept();
				new CreateServerThread(socket);
			}
		} finally {
			close();
		}
	}

	/*
	 * 实现广播通信的内部类线程,对登陆的每个客户端发送信息
	 */
	class Broadcast extends Thread {
		public Broadcast() {
			start();
		}

		public void run() {
			while (true) {
				if (!isClear) {
					String tmp = (String) Message_Array.getFirst();
					for (int i = 0; i < Threader.size(); i++) {
						CreateServerThread client = (CreateServerThread) Threader
								.get(i);
						client.sendMessage(tmp);
					}
					Message_Array.removeFirst();
					isClear = Message_Array.size() > 0 ? false : true;
				}
			}
		}
	}

	/*
	 * 内部接收Socket的线程处理类
	 */
	class CreateServerThread extends Thread {
		private Socket client;
		private BufferedReader in;
		private PrintWriter out;
		private String Username;

		public CreateServerThread(Socket socket) {
			client = socket;
			try {
				in = new BufferedReader(new InputStreamReader(
						client.getInputStream()));
				out = new PrintWriter(client.getOutputStream(), true);
				out.println("--- Welcome to this chatroom ---");
				out.println("Input your nickname:");
				start();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void sendMessage(String msg) {
			out.println(msg);
		}

		public void run() {
			try {
				int flag = 0;
				String line = in.readLine();
				while (!line.equals("bye")) {
					if (line.equals("l")) {
						out.println(listOnlineUsers());
						line = in.readLine();
						continue;
					}
					if (flag++ == 0) {
						Username = line;
						User_List.add(Username);
						out.println(listOnlineUsers());
						Threader.add(this);
						pushMessage("[< " + Username + " come on in >]");
					} else {
						pushMessage("<" + Username + ">" + line);
					}
					line = in.readLine();
				}
				out.println("--- See you, bye! ---");
				client.close();
			} catch (IOException e) {
			} finally {
				try {
					client.close();
				} catch (IOException e) {
				}
				Threader.remove(this);
				User_List.remove(Username);
				pushMessage("[< " + Username + " left>]");
			}
		}

		private String listOnlineUsers() {
			String s = "-+- Online list -+-1512";
			for (int i = 0; i < User_List.size(); i++) {
				s += "[" + User_List.get(i) + "]1512";
			}
			s += "-+---------------------+-";
			return s;
		}

		private void pushMessage(String msg) {
			Message_Array.addLast(msg);
			isClear = false;
		}
	}
	
	   /*
	    * 主调试函数main()
	    */
		public static void main(String[] args) {
			// TODO Auto-generated method stub
	        try {
				new Mutual_Server();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
