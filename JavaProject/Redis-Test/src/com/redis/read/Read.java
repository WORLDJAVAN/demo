package com.redis.read;

import redis.clients.jedis.Jedis;

public class Read {
	
    public void test(){
    	
    	for(int i=0;i<10;i++){
    		
    	};

	Jedis jedis = new Jedis("localhost");	   
    System.out.println(jedis.get("name"));
    jedis.del("name");
    jedis.set("key","zoulong");
    System.out.println(jedis.get("key"));
    }
}
 