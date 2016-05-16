package com.greenorbs.websocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.log4j.Logger;
import org.apache.zookeeper.data.Stat;

import com.greenorbs.service.VideoServiceI;
import com.greenorbs.util.ConfigUtil;
import com.greenorbs.util.SpringApplicationContextHolder;
import com.greeorbs.zookeeper.ZookeeperClient;
public class DemoVideoHandle {
	
	private static Logger log = Logger.getLogger(DemoVideoHandle.class);
	private VideoServiceI videoService = (VideoServiceI)SpringApplicationContextHolder.getSpringBean("VideoService");
	String Path = this.getClass().getClassLoader().getResource("/").getPath();
	int last =Path.lastIndexOf("WEB-INF");
	String realPath = Path.substring(1,last);
	String http_node=ConfigUtil.get("http_node");//zookper节点名称
	Stat stat = new Stat();
	SimpleDateFormat myFmt=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	public void start() {
				ZookeeperClient.zookperStart();//启动zookeeper
		        final PathChildrenCache cache= new PathChildrenCache(ZookeeperClient.getClient(),http_node,true);
		    	System.out.println("DemoVideoHandle start()。。。。");
		        try {
					cache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.error("服务器连接失败。。。。");
					System.out.println("服务器连接失败。。。。");
//					e.printStackTrace();
				}
		        cache.getListenable().addListener(new PathChildrenCacheListener() {//监听zookeeper节点
					
					@Override
					public void childEvent(CuratorFramework client, PathChildrenCacheEvent event)
							throws Exception {
						// TODO Auto-generated method stub
						switch(event.getType()){
						case CHILD_ADDED://当zookeeper里面添加节点时 生成页面并存入数据库
							if(event.getData().getData()==null){
								log.error("add get data empty。。。。");
							}else{
								String path = event.getData().getPath();//获取新添加节点路径
								String mac = path.substring(path.lastIndexOf("/")+1);//截取获得mac
								String runtime = myFmt.format(new Date(event.getData().getStat().getCtime()));//获得该节点的安装时间
								try {
									BufferedReader reader = new BufferedReader(new FileReader(java.net.URLDecoder.decode(realPath+"Demo.jsp", "UTF-8")));
									BufferedWriter writer = new BufferedWriter(new FileWriter(java.net.URLDecoder.decode(realPath+mac+".jsp", "UTF-8")));
									String str = null;
									while ((str = reader.readLine()) != null) {
										str=str.replaceAll("Number", mac);
										writer.write(str);
										writer.newLine();
									}
									writer.flush();
									reader.close();
									writer.close();
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									log.error("未找到Demo.jsp。。。。");
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									log.error("文件流io异常。。。。");
									e.printStackTrace();
								}
								try{
									if(videoService.selectmac(mac)==0){
									videoService.insertMacUrlHtml(mac, new String(event.getData().getData(),"utf-8"), "http://127.0.0.1:8080/video/"+mac+".jsp",runtime);
									}else{
										videoService.updateUrl(mac, new String(event.getData().getData(),"utf-8"));
									}
								}catch(Exception e){
									log.error("sql insert exception。。。。");
								}
								System.out.println("html add over。。。。。");
							}
							break;
						case CHILD_UPDATED://当zookeeper里面更新节点时 更新数据库
							if(event.getData().getData()==null){
								log.error("update get data empty。。。。");
							}else{
								String path = event.getData().getPath();
								String mac = path.substring(path.lastIndexOf("/")+1);
								
								String newUrl=new String(event.getData().getData(),"utf-8");//by myp
								videoService.updateUrl(mac, newUrl);
								
							    //群发消息 by myp
						        for(DemoMessageInbound item: DemoMessageInbound.webSocketSet){            
						            if(item.message.equals(mac))
									{
										  item.pushMessage(mac+"+"+newUrl);
									}
						        }
								
								System.out.println("html update over 。。。。。");
							}
							break;
						case CHILD_REMOVED://当zookeeper里面删除节点时 删除页面和数据库信息
							if(event.getData().getData()==null){
								log.error("remove get data empty。。。。");
							}else{
								String path = event.getData().getPath();
								String mac = path.substring(path.lastIndexOf("/")+1);
								try{
								videoService.deleteUrl(mac);//mac is offline,set url empty
								new File(java.net.URLDecoder.decode(realPath+mac+".jsp", "UTF-8")).delete();
								
								 //群发消息 by myp
						        for(DemoMessageInbound item: DemoMessageInbound.webSocketSet){            
						            if(item.message.equals(mac))
									{
										  item.pushMessage(mac+"+urlEmpty");
									}
						        }
								
								}catch(Exception e){
									log.error("delete html empty exist。。。。");
								}
								System.out.println("html remove over。。。。。");
							}
							break;
						case CONNECTION_RECONNECTED:
							System.out.println("zookeeper connection reconnected。。。。。");
							break;
						case CONNECTION_SUSPENDED:
							System.out.println("zookeeper connection suspended。。。。。");
							break;
						case CONNECTION_LOST:
							System.out.println("zookeeper connection lost。。。。。");
							break;
						default:
							log.error("catch don't know exception.....");
							break;
						}
						
					}
				});
		        
		        
	}
	
	public void close() {
		
	}
	
	
}
