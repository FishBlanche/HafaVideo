package com.greenorbs.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenorbs.dao.ClearDaoI;
import com.greenorbs.service.ClearServiceI;

@Service
public class ClearServiceImpl implements ClearServiceI{

	@Autowired
	private ClearDaoI clearDao;
	
	
	
	/*public ClearDaoI getClearDao() {
		return clearDao;
	}



	public void setClearDao(ClearDaoI clearDao) {
		this.clearDao = clearDao;
	}*/

	public void clearAllData(){
		
		clearDao.clearAllData();
		
	}
	
}
