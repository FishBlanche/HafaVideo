package com.greenorbs.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greenorbs.util.SessionInfo;



@Controller
@RequestMapping("/seesionController")
public class SeesionController {
	
	@ResponseBody
	@RequestMapping("/addSeesion")
	public void addSeesion(HttpSession session,HttpServletRequest request){
//		SessionInfo sessionInfo = new SessionInfo();
//		sessionInfo.setIp("111111");
//		session.setAttribute("sessionInfo",sessionInfo);
	}

}
