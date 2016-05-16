package com.greenorbs.service;

import java.util.List;

import com.greenorbs.model.Video;

/**
 * 
 * 
 * @author liangxing
 * 
 */
public interface VideoServiceI {
	
	//获得 ID MAC URL HTML
	public List<Video> getIdMacUrlHtml();
	
	//获得总行数
	public int getcount();
	
	//更新HTML
	public void updateHtml(String mac,String html);
	
	//获得URL
	public String getURL(String mac);
	
	//插入MAC RUL HTML
	public void insertMacUrlHtml(String mac,String url,String html,String runtime);
	
	//更新RUL
	public void updateUrl(String mac,String url);
	
	//删除url
	public void deleteUrl(String mac);
	
	//select mac
	public int selectmac(String mac);
		

}
