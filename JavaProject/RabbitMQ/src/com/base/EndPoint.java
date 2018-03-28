/** 
 * Project Name:mq 
 * File Name:EndPoint.java 
 * Package Name:org.yannis.mq.rabbitMQ 
 * Date:2016骞�鏈�3鏃ヤ笂鍗�0:50:04 
 * Copyright (c) 2016, zhaoyjun0222@gmail.com All Rights Reserved. 
 * 
 */  
      
package com.base;
  
/** 
 * ClassName:EndPoint <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2016骞�鏈�3鏃�涓婂崍10:50:04 <br/> 
 * @author   Yanjun Zhao , zhaoyjun0222@gmail.com
 * @version  1.0 
 * @since    JDK 1.7 
 * @see       
 */
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
 
/**
 * Represents a connection with a queue
 * @author syntx
 *
 */
public abstract class EndPoint{
     
    protected Channel channel;
    protected Connection connection;
    protected String endPointName;
     
    public EndPoint(String endpointName) throws IOException, TimeoutException{
         this.endPointName = endpointName;
         
         //Create a connection factory
         ConnectionFactory factory = new ConnectionFactory();
         
         //hostname of your rabbitmq server
         factory.setHost("127.0.0.1");
         factory.setPort(5672);//MQ绔彛
         factory.setUsername("guest");//MQ鐢ㄦ埛鍚�
         factory.setPassword("guest");//MQ瀵嗙爜
       
         
         //getting a connection
         connection = factory.newConnection();
         
         //creating a channel
         channel = connection.createChannel();
         
         //declaring a queue for this channel. If queue does not exist,
         //it will be created on the server.
         channel.queueDeclare(endpointName, false, false, false, null);
    }
     
     
    /**
     * 鍏抽棴channel鍜宑onnection銆傚苟闈炲繀椤伙紝鍥犱负闅愬惈鏄嚜鍔ㄨ皟鐢ㄧ殑銆�
     * @throws IOException
     * @throws TimeoutException 
     */
     public void close() throws IOException, TimeoutException{
         this.channel.close();
         this.connection.close();
     }
}
  