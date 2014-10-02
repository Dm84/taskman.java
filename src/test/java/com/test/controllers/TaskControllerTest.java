package com.test.controllers;

import java.sql.Timestamp;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.test.service.TaskService;
import com.test.domain.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TaskControllerTest {
	
	private ApplicationContext appContext;
	private TaskController controller;
	
	public TaskControllerTest() {
		
		appContext = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");		
		controller = appContext.getBean("taskController", TaskController.class);   
	}
	
	@Test
	public void create() {
		
		Task testTask = new Task();
		
		testTask.setDescription("test desc");
		testTask.setDeadline(new Timestamp(777));
		testTask.setCompleteness(false);
		
		controller.createTask(testTask);
	}
	
}