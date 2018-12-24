package com.sdlh.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
 
public class RedisTemplateUtil {
	/**
	 * 默认保存时间20分钟
	 */
	private int defaultPeriod = 20*60*1000;
	
	private JedisPool jedisPool;
	
    public void set(String key, String value) {
    	Jedis jedis = jedisPool.getResource();
    	jedis.set(key, value);
    	jedis.expire(key, defaultPeriod);
    	jedis.close();
    }

    
    public void set(String key, String value,int expriod) {
    	Jedis jedis = jedisPool.getResource();
    	jedis.set(key, value);
    	jedis.expire(key, expriod);
    	jedis.close();
    }
    
    public String get(String key) {
    	Jedis jedis = jedisPool.getResource();
    	String result = jedis.get(key);
    	jedis.close();
    	return result;
    }
    
    
    public void expire(String key,int period) {
    	Jedis jedis = jedisPool.getResource();
    	jedis.expire(key, defaultPeriod);
    	jedis.close();
    }
    
    public void expireAt(String key,long unixTime) {
    	Jedis jedis = jedisPool.getResource();
    	jedis.expireAt(key, unixTime);
    	jedis.close();
    }
    
	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
    
}