 package com.greenorbs.websocket;

import java.io.IOException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

import com.greenorbs.service.VideoServiceI;
import com.greenorbs.util.SpringApplicationContextHolder;
@ServerEndpoint("/DemoWebSocket")
public class DemoMessageInbound {
	public String message;
	
	private Session mySession;//by myp
	 //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    public static CopyOnWriteArraySet<DemoMessageInbound> webSocketSet = new CopyOnWriteArraySet<DemoMessageInbound>();//by myp
     //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;//by myp
    
    
	private VideoServiceI videoService = (VideoServiceI)SpringApplicationContextHolder.getSpringBean("VideoService");

	@OnOpen  
    public void open(Session session,EndpointConfig config) { 
		
	    this.mySession=session;//by myp
		webSocketSet.add(this);     //加入set中  by myp
   	    addOnlineCount();           //在线数加1 by myp
    }  
	/*websocket 推送播放地址给前端页面
	 * 
	 * */
	@OnMessage  
    public void inMessage(Session session,String message) { 
		 
        try {  
        	//String url = videoService.getURL(message);//BY LIANGXING
        	this.message = message;
         
        	String url = message+"+"+videoService.getURL(message);//BY MYP
        	mySession.getBasicRemote().sendText(url);  
            System.out.println("服务器已成功推送 "+url+" 给页面   "+message+".jsp");
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
	
	@OnClose  
    public void end(Session session) {  
		webSocketSet.remove(this);  //从set中删除
		 subOnlineCount();           //在线数减1    
		System.out.println("关闭页面" +message+".jsp");
    } 
	@OnError
    public void onError(Session session, Throwable error){
	        System.out.println("WebSocket error!");
	        error.printStackTrace();
	}
	
	
	 public void pushMessage(String newVideoUrl) { //by myp
			  try {  
	        
	        	mySession.getBasicRemote().sendText(newVideoUrl);  
	            System.out.println("pushMessage "+newVideoUrl+" 给页面   "+message+".jsp");
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	
	  public static synchronized int getOnlineCount() {//by myp
	        return onlineCount;
	  }
	 
	  public static synchronized void addOnlineCount() { //by myp
	    	DemoMessageInbound.onlineCount++;
	  }
	     
	  public static synchronized void subOnlineCount() { //by myp
	    	DemoMessageInbound.onlineCount--;
	  }
    
}
