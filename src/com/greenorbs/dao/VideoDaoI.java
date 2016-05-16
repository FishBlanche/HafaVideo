package com.greenorbs.dao;

import java.util.List;

import com.greenorbs.model.Video;

public interface VideoDaoI extends BaseDaoI<Video>{

	public List<Video> getIdMacUrlHtml();
	
	public int getcount();
	
	public void updateHtml(String mac,String html);
	
	public String getURL(String mac);
	
	public void insertMacUrlHtml(String mac,String url,String html,String runtime);
	
	public void updateUrl(String mac,String url);
	
	public void deleteUrl(String mac);
	
	public int selectmac(String mac);
	
}
