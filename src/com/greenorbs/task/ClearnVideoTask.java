package com.greenorbs.task;

import com.greenorbs.service.ClearServiceI;

public class ClearnVideoTask {

	public ClearServiceI clearService;
	
	
	
	public ClearServiceI getClearService() {
		return clearService;
	}



	public void setClearService(ClearServiceI clearService) {
		this.clearService = clearService;
	}

/*
 * 每天12点全部重新生成页面
 * 
 * 
 * */

	public void task() {
		
		System.out.println("ClearnVideoTask task executor.....");
		clearService.clearAllData();
		
	}
	
}
