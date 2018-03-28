package com.msmq;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class ActiveXComp {

	private static ActiveXComponent dotnetCom = null;

	private static Variant va = null;

	private static Dispatch dis = null;

	public static void main(String[] args) {
		// ShineHttp.Main为com组件入口主方法
		dotnetCom = new ActiveXComponent("ShineHttp.Main");
		// 获取通信对象
		dis = Dispatch.call(dotnetCom, "GetHttpOP").toDispatch();
		System.out.println("获取Http通讯接口对象成功。");
		// 调用通讯对象登陆方法
		va = Dispatch.call(dis, "Login", "url", "user", "pwd");
		System.out.println(va.toBoolean());
		// 调用通讯对象发送数据方法
		va = Dispatch.call(dis, "SendData", new Variant("报文内容"));
		System.out.println("SendData" + "结束！");
		// 调用通讯对象接收数据方法，注意第三个参数
		Variant v1 = new Variant("", true);
		va = Dispatch.call(dis, "RecvData", v1);
		System.out.println("对方系统返回的数据包为：" + v1.toString() + "++++"
				+ va.toBoolean());
	}
}
