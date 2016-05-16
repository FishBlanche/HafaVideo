package com.greenorbs.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.greenorbs.dao.VideoDaoI;
import com.greenorbs.model.Video;

@Repository
public class VideoDaoImpl extends BaseDaoImpl<Video> implements VideoDaoI{
	
	public List<Video> getIdMacUrlHtml(){
		List<Video> videoList = new ArrayList<Video>();
		String sql="select id,mac,url,html from camerainfo";
		List<Object[]> list = findBySql(sql);
		for (Object[] objects : list) {
			Video video = new Video(objects[0]==null?"":objects[0].toString(),objects[1]==null?"":objects[1].toString(),objects[2]==null?"":objects[2].toString(),objects[3]==null?"":objects[3].toString());
			videoList.add(video);
		}
		
		return videoList;
	}
	
	public int getcount(){
		
		return 0;
	}

	public void updateHtml(String mac,String html){
	
		String sql="update camerainfo set html='"+html+"'"+" where mac='"+mac+"'";
		executeSql(sql);
		
	}
	public String getURL(String mac){
		String sql="select url,mac from camerainfo where mac='"+mac+"'";
		List<Object[]> list = findBySql(sql);
		return list.get(0)[0]==null?"":list.get(0)[0].toString();
	}
	
	public void insertMacUrlHtml(String mac,String url,String html,String runtime){
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mac", mac);
		params.put("url", url);
		params.put("html", html);
		params.put("runtime", runtime);
		String sql="insert into camerainfo (mac,url,html,runtime) values (:mac,:url,:html,:runtime)";
		executeSql(sql, params);
		
	}
	public void updateUrl(String mac,String url){
		
		String sql="update camerainfo set url='"+url+"'"+" where mac='"+mac+"'";
		executeSql(sql);
		
	}
	public void deleteUrl(String mac){
		String url = "";
		String sql="update camerainfo set url='"+url+"'"+" where mac='"+mac+"'";
		executeSql(sql);
	}
	public int selectmac(String mac){
		String sql = "select count(mac) from camerainfo where mac='"+mac+"'";
		return countBySql(sql);
	}
	
}
