package com.test.controllers;

import java.sql.Timestamp;

import org.junit.*;

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
	public void add() {		
		Task testTask = new Task();
		
		testTask.setDescription("test desc");
		testTask.setDeadline(new Timestamp(777));
		testTask.setCompleted(false);
		
		controller.add(testTask);
	}
	
}