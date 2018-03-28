package com.msmq;

import java.io.UnsupportedEncodingException;

import ionic.Msmq.Message;
import ionic.Msmq.MessageQueueException;
import ionic.Msmq.Queue;

public class MsMqDemo {

	public static void main(String[] args) {
		try {
			/* 创建连接Queue队列  */
	        /* 连接方式一 指定TCP：xxxxx */
		 //  Queue queue = new Queue("Direct=TCP:127.0.0.1\\private$\\TestQueue");
			
			 /* 连接方式二 指定 OS：xxxxx（IP或计算机名）前提都是在同一个域中  */
		  Queue queue = new Queue("Direct=OS:localhost\\private$\\testQueue");
			

		//  Queue queue= new Queue("DIRECT=TCP:10.19.42.47\\private$\\test");  //彦军机器
		    
		  
            /* 设置信息体 标签，可以用做消息的类别来用 */
		    String label = "ms_label"; 	    
		    /* 设置信息体  body */
			String body = "Hello, World";
			
			/*消息编号 可以藏密钥匙或其他扩展用法  */
			byte[] correlationId = { 0, 'A', 'B', 6, 8, 9 };

			try {
				
				/* 创建信息 msg */
				Message	sed_msg = new Message(body, label, correlationId);
				/* 发送信息  */
				queue.send(sed_msg);
				
				/* 接收信息  */
				Message re_msg = queue.receive();
			
				/* 获取信息Body内容 */
				String msg_body = re_msg.getBodyAsString();
				
				String msg_CorrelationId = re_msg.getCorrelationIdAsString();
				
				String msg_label = re_msg.getLabel();
				
				byte[] kk = msg_CorrelationId.getBytes();

				for(int i=0; i<kk.length; i++){
				System.out.println(kk[i]);}
				
				/* 信息Body的使用  */
				System.out.println(msg_label+msg_body);
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
		} catch (MessageQueueException ex1) {
			System.out.println("Put failure: " + ex1.toString());
		}

	}

}
