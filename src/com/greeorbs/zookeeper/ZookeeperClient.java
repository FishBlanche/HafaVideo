package com.greeorbs.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.greenorbs.util.ConfigUtil;

public class ZookeeperClient {
	static String zookeeper_id=ConfigUtil.get("zookeeper_id");
	private static final RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);//1秒钟连接一次 连接3次
//	RetryPolicy retryPolicy = new RetryNTimes(1, 1000);
//    CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.0.206:2181",5000,5000,retryPolicy);
	private static final CuratorFramework client = CuratorFrameworkFactory.builder()
			                                         .connectString(zookeeper_id)
			                                         .sessionTimeoutMs(5000)
			                                         .connectionTimeoutMs(5000)
			                                         .retryPolicy(retryPolicy)
			                                         .build();
	
	public static void zookperStart(){
		client.start();
	}
	
	public static CuratorFramework getClient(){
		
		return client;
	}
	
	public static void zookeeperClose(){
		client.close();
	}

}
