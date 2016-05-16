package com.greenorbs.service.impl;

import java.util.List;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenorbs.dao.VideoDaoI;
import com.greenorbs.model.Video;
import com.greenorbs.service.VideoServiceI;


@Service
public class VideoServiceImpl implements VideoServiceI {
	
	
	@Autowired
	private VideoDaoI allocateDao;

	public List<Video> getIdMacUrlHtml(){
		
		return allocateDao.getIdMacUrlHtml();
	}
	
	public int getcount(){
		
		
		return allocateDao.getcount();
	}
	
	public void updateHtml(String mac,String html){
		
		allocateDao.updateHtml(mac,html);
	}
	
	public String getURL(String mac){
		
		return allocateDao.getURL(mac);
	}
	
	public void insertMacUrlHtml(String mac,String url,String html,String runtime){
		
		allocateDao.insertMacUrlHtml(mac, url, html,runtime);
		
	}
	
	public void updateUrl(String mac,String url){
		allocateDao.updateUrl(mac, url);
	}
	public void deleteUrl(String mac){
		allocateDao.deleteUrl(mac);
	}
	public int selectmac(String mac){
		
		return allocateDao.selectmac(mac);
	}
}
