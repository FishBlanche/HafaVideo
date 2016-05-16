package com.greeorbs.zookeeper;


import com.greenorbs.service.ClearServiceI;
import com.greenorbs.util.BeanFactoryUtil;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;


/**
 * 监听在线用户上线下线
 * 
 * @author liangxing
 * 
 */
public class initializeVideo implements ServletContextListener,
		ServletContextAttributeListener, HttpSessionListener,
		HttpSessionAttributeListener, HttpSessionActivationListener,
		HttpSessionBindingListener, ServletRequestListener,
		ServletRequestAttributeListener {

	private static final Logger logger = Logger.getLogger(initializeVideo.class);


	public initializeVideo() {
		
	}

	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
	}

	/**
	 * 向session里增加属性时调用(用户成功登陆后会调用)
	 */
	public void attributeAdded(HttpSessionBindingEvent evt) {
	}

	/**
	 * 服务器启动完成时连接zookeeper生成页面
	 */
	public void contextInitialized(ServletContextEvent evt) {
		
		System.out.println("initializeVideo contextInitialized。。。。");
		
		ClearServiceI onlineService = (ClearServiceI) BeanFactoryUtil
				.getBean("clearServiceImpl");
		onlineService.clearAllData();
	}

	public void sessionDidActivate(HttpSessionEvent arg0) {
	}

	public void valueBound(HttpSessionBindingEvent arg0) {
	}

	public void attributeAdded(ServletContextAttributeEvent arg0) {
	}

	public void attributeRemoved(ServletContextAttributeEvent arg0) {
	}

	/**
	 * session销毁(用户退出系统时会调用)
	 */
	public void sessionDestroyed(HttpSessionEvent evt) {


	}

	public void attributeRemoved(HttpSessionBindingEvent arg0) {
	}

	public void attributeAdded(ServletRequestAttributeEvent evt) {
	}

	public void valueUnbound(HttpSessionBindingEvent arg0) {
	}

	public void sessionWillPassivate(HttpSessionEvent arg0) {
	}

	public void sessionCreated(HttpSessionEvent arg0) {
	}

	public void attributeReplaced(HttpSessionBindingEvent arg0) {
	}

	public void attributeReplaced(ServletContextAttributeEvent arg0) {
	}

	public void attributeRemoved(ServletRequestAttributeEvent arg0) {
	}

	public void contextDestroyed(ServletContextEvent evt) {
		logger.debug("服务器关闭");
	}

	public void attributeReplaced(ServletRequestAttributeEvent arg0) {
	}

	public void requestInitialized(ServletRequestEvent arg0) {
	}

}
