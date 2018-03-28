/** 
 * Project Name:mq 
 * File Name:Test.java 
 * Package Name:org.yannis.mq.rabbitMQ 
 * Date:2016骞�鏈�3鏃ヤ笂鍗�0:52:18 
 * Copyright (c) 2016, zhaoyjun0222@gmail.com All Rights Reserved. 
 * 
 */  
      
package com.base;
  
/** 
 * ClassName:Test <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * @author   Yanjun Zhao , zhaoyjun0222@gmail.com
 * @version  1.0 
 * @since    JDK 1.7 
 * @see       
 */
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
 
public class Test {
    public Test() throws Exception{
         
        QueueConsumer consumer = new QueueConsumer("queue");
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
         
        Producer producer = new Producer("queue");
         
        for (int i = 0; i < 10; i++) {
            HashMap message = new HashMap();
            message.put("message number", i);
            producer.sendMessage(message);
            System.out.println("Message Number "+ i +" sent.");
        }
    }
     
    /**
     * @param args
     * @throws SQLException
     * @throws IOException
     */
    public static void main(String[] args) throws Exception{
      new Test();
    }
}
  