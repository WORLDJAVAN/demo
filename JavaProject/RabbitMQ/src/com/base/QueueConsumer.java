/** 
 * Project Name:mq 
 * File Name:QueueConsumer.java 
 * Package Name:org.yannis.mq.rabbitMQ 
 * Date:2016骞�鏈�3鏃ヤ笂鍗�0:51:51 
 * Copyright (c) 2016, zhaoyjun0222@gmail.com All Rights Reserved. 
 * 
 */  
      
package com.base;
  
/** 
 * ClassName:QueueConsumer <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2016骞�鏈�3鏃�涓婂崍10:51:51 <br/> 
 * @author   Yanjun Zhao , zhaoyjun0222@gmail.com
 * @version  1.0 
 * @since    JDK 1.7 
 * @see       
 */
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.SerializationUtils;
 
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
 
 
/**
 * 璇诲彇闃熷垪鐨勭▼搴忕锛屽疄鐜颁簡Runnable鎺ュ彛銆�
 * @author syntx
 *
 */
public class QueueConsumer extends EndPoint implements Runnable, Consumer{
     
    public QueueConsumer(String endPointName) throws IOException, TimeoutException{
        super(endPointName);       
    }
     
    public void run() {
        try {
            //start consuming messages. Auto acknowledge messages.
            channel.basicConsume(endPointName, true,this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * Called when consumer is registered.
     */
    public void handleConsumeOk(String consumerTag) {
        System.out.println("Consumer "+consumerTag +" registered");    
    }
 
    /**
     * Called when new message is available.
     */
    public void handleDelivery(String consumerTag, Envelope env,
            BasicProperties props, byte[] body) throws IOException {
        Map map = (HashMap)SerializationUtils.deserialize(body);
        System.out.println("Message Number "+ map.get("message number") + " received.");
         
    }
 
    public void handleCancel(String consumerTag) {}
    public void handleCancelOk(String consumerTag) {}
    public void handleRecoverOk(String consumerTag) {}
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {}
}
  