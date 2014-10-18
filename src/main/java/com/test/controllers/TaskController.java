package com.test.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.apache.log4j.Logger;

import com.test.domain.Task;
import com.test.service.TaskService;

@RestController
public class TaskController {

	private static final Logger logger = Logger.getLogger(TaskController.class);
	private TaskService service;
	
	@Autowired
	TaskController(@Qualifier("taskService") TaskService service) {
		this.service = service;
	}
	
	@RequestMapping("/task/test")
	public String test(Model model) {
		return "view";
	}
		
	@RequestMapping("/task/create")
	public String add(@ModelAttribute Task task) {		
		service.add(task);
		return "{}";
	}
	
	@RequestMapping("/task/search")
	public java.util.Map<Integer, Task> search(String query) {
		return service.find(query);
	}
	
	@RequestMapping("/task/{id}/complete")
	public String complete(@PathVariable Integer id) {
		service.complete(id);
		return "{}";
	}

	@RequestMapping("/task/list")
	public java.util.Map<Integer, Task> list() {		
		return service.listAll();
	}
	
	class Response {
		public Object response;
	}
	
}