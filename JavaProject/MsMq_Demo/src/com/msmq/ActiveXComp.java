package com.msmq;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class ActiveXComp {

	private static ActiveXComponent dotnetCom = null;

	private static Variant va = null;

	private static Dispatch dis = null;

	public static void main(String[] args) {
		// ShineHttp.MainΪcom������������
		dotnetCom = new ActiveXComponent("ShineHttp.Main");
		// ��ȡͨ�Ŷ���
		dis = Dispatch.call(dotnetCom, "GetHttpOP").toDispatch();
		System.out.println("��ȡHttpͨѶ�ӿڶ���ɹ���");
		// ����ͨѶ�����½����
		va = Dispatch.call(dis, "Login", "url", "user", "pwd");
		System.out.println(va.toBoolean());
		// ����ͨѶ���������ݷ���
		va = Dispatch.call(dis, "SendData", new Variant("��������"));
		System.out.println("SendData" + "������");
		// ����ͨѶ����������ݷ�����ע�����������
		Variant v1 = new Variant("", true);
		va = Dispatch.call(dis, "RecvData", v1);
		System.out.println("�Է�ϵͳ���ص����ݰ�Ϊ��" + v1.toString() + "++++"
				+ va.toBoolean());
	}
}
