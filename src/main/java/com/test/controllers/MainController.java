package com.test.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
class MainController {

	private static final Logger logger = Logger.getLogger(MainController.class);
	 
//	public MainController() {
//		
//		if(logger.isDebugEnabled()) {
//			logger.debug("MainController is constructed!");
//		}	
//		
//	}
	
	@RequestMapping("/main")
	public ModelAndView index() {
		
		//logs debug message
		if(logger.isDebugEnabled()){
			logger.debug("getWelcome is executed!");
		}	
		
		return new ModelAndView("view");
	}
	
//	@Override
//	protected ModelAndView handleRequestInternal(	HttpServletRequest request, 
//													HttpServletResponse response) {
//		return new ModelAndView("view");	
//	}
}