package com.greenorbs.dao;

import com.greenorbs.model.Video;

public interface ClearDaoI extends BaseDaoI<Video> {
	
	public void clearAllData();

}
