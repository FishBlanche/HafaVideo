package com.greenorbs.model;

import java.io.Serializable;

public class Video implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String mac;
	private String url;
	private String html;
	
	public Video(String id,String mac,String url,String html){
		this.id = id;
		this.mac = mac;
		this.url = url;
		this.html = html;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	
	

}
