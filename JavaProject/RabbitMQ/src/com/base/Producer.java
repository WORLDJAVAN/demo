/** 
 * Project Name:mq 
 * File Name:Producer.java 
 * Package Name:org.yannis.mq.rabbitMQ 
 * Date:2016骞�鏈�3鏃ヤ笂鍗�0:51:03 
 * Copyright (c) 2016, zhaoyjun0222@gmail.com All Rights Reserved. 
 * 
 */  
      
package com.base;
  
/** 
 * ClassName:Producer <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2016骞�鏈�3鏃�涓婂崍10:51:03 <br/> 
 * @author   Yanjun Zhao , zhaoyjun0222@gmail.com
 * @version  1.0 
 * @since    JDK 1.7 
 * @see       
 */
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.SerializationUtils;
 
 
/**
 * The producer endpoint that writes to the queue.
 * @author syntx
 *
 */
public class Producer extends EndPoint{
     
    public Producer(String endPointName) throws IOException, TimeoutException{
        super(endPointName);
    }
 
    public void sendMessage(Serializable object) throws IOException {
        channel.basicPublish("",endPointName, null, SerializationUtils.serialize(object));
    }  
}
  