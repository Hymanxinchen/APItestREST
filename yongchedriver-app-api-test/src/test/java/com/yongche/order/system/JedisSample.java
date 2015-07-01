package com.yongche.order.system;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.sound.midi.SysexMessage;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class JedisSample {

	@Test
	public void testConnect() throws Exception{
		Jedis jedis = new Jedis("10.0.11.223",6379);
//		jedis.sadd("testSet", "s1");
//		jedis.sadd("testSet", "s2");
//		jedis.sadd("testSet", "s3");
//		jedis.sadd("testSet", "s4");
//		jedis.sadd("testSet", "s5");
//		Set set = jedis.smembers("testSet");
//		  Iterator t1=set.iterator() ;
//		  while(t1.hasNext()){
//		   Object obj1=t1.next();
//		   System.out.println(obj1);
//		  }
//		System.out.println(jedis.get("testSet"));
//		 Set keys = jedis.keys("*");//列出所有的key，查找特定的key如：redis.keys("foo")
//		 Iterator t1=keys.iterator() ;
//		 while(t1.hasNext()){
//		    Object obj1=t1.next();
//		    System.out.println(obj1);
//		    Set set1 = jedis.smembers(obj1.toString());
//		    Iterator t2 = set1.iterator();
//		    while(t2.hasNext()){
//				   Object obj2=t2.next();
//				   System.out.println(obj2);
//				  }
//		   }
//		System.out.println(jedis.type("1067"));
//		System.out.println(jedis.hgetAll("WEIDAO_OAUTH2_ffe31f846f5ee9d20d9595b4cff10a8cf4129c78").toString());
//		System.out.println(jedis.mget("client_id"));
		System.out.println(jedis.hgetAll("WEIDAO_OAUTH2_ffe31f846f5ee9d20d9595b4cff10a8cf4129c78"));
//		jedis.select("");
		System.out.println(jedis.hgetAll("recommend_drivers_id:2005482190"));
//		byte[] str = jedis.get("recommend_drivers_id:2005482172").getBytes();
//		System.out.println("dfdj" + str);
//		String sss = (String)this.deSeialize(str);
//		System.out.print(sss);
//		byte[] bytes = str.getBytes("utf-8");
//		String str1 = new String(bytes,"utf-8");
//		System.out.println(str1);
//	    byte[] user = jedis.get("get:user:10009".getBytes());
//		String str = new String(user,"UTF-8");
//		System.out.println(str);
		
//		System.out.println(jedis.get("client_id"));
//		System.out.println(jedis.get("1067").toString());
//		System.out.println(jedis.hget("clien_id", "WEIDAO_OAUTH2_ffe31f846f5ee9d20d9595b4cff10a8cf4129c78"));
//		System.out.println("smember:" + jedis.smembers("WEIDAO_OAUTH2_ffe31f846f5ee9d20d9595b4cff10a8cf4129c78"));
		 
	}
	
	 /**
     * 反序列化
     * 
     * @param bytes
     * @return
     */
    public Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {

        }
        return null;
    }
    
    
    public Object deSeialize(byte[] bytes) {
    	ByteArrayInputStream byteArrayOutputStream = null;
    	try {
    	byteArrayOutputStream = new ByteArrayInputStream(bytes);
    	ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayOutputStream);
    	return objectInputStream.readObject();
    	} catch (Exception e) {
    	System.out.println(e+ "deserialize exception");

    	}
    	return null;
    	}
}
