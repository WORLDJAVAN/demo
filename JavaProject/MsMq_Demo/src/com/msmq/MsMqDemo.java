package com.msmq;

import java.io.UnsupportedEncodingException;

import ionic.Msmq.Message;
import ionic.Msmq.MessageQueueException;
import ionic.Msmq.Queue;

public class MsMqDemo {

	public static void main(String[] args) {
		try {
			/* ��������Queue����  */
	        /* ���ӷ�ʽһ ָ��TCP��xxxxx */
		 //  Queue queue = new Queue("Direct=TCP:127.0.0.1\\private$\\TestQueue");
			
			 /* ���ӷ�ʽ�� ָ�� OS��xxxxx��IP����������ǰ�ᶼ����ͬһ������  */
		  Queue queue = new Queue("Direct=OS:localhost\\private$\\testQueue");
			

		//  Queue queue= new Queue("DIRECT=TCP:10.19.42.47\\private$\\test");  //�������
		    
		  
            /* ������Ϣ�� ��ǩ������������Ϣ��������� */
		    String label = "ms_label"; 	    
		    /* ������Ϣ��  body */
			String body = "Hello, World";
			
			/*��Ϣ��� ���Բ���Կ�׻�������չ�÷�  */
			byte[] correlationId = { 0, 'A', 'B', 6, 8, 9 };

			try {
				
				/* ������Ϣ msg */
				Message	sed_msg = new Message(body, label, correlationId);
				/* ������Ϣ  */
				queue.send(sed_msg);
				
				/* ������Ϣ  */
				Message re_msg = queue.receive();
			
				/* ��ȡ��ϢBody���� */
				String msg_body = re_msg.getBodyAsString();
				
				String msg_CorrelationId = re_msg.getCorrelationIdAsString();
				
				String msg_label = re_msg.getLabel();
				
				byte[] kk = msg_CorrelationId.getBytes();

				for(int i=0; i<kk.length; i++){
				System.out.println(kk[i]);}
				
				/* ��ϢBody��ʹ��  */
				System.out.println(msg_label+msg_body);
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
		} catch (MessageQueueException ex1) {
			System.out.println("Put failure: " + ex1.toString());
		}

	}

}
