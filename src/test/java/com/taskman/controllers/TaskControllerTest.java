package com.taskman.controllers;

import com.taskman.repository.entity.Task;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TaskControllerTest {
	
	private ApplicationContext appContext;
	private TaskController controller;
	
	public TaskControllerTest() {
		
		appContext = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");		
		controller = appContext.getBean("taskController", TaskController.class);   
	}
	
	public void add() {		
		Task testTask = new Task();
		
		testTask.setDescription("test desc");
		testTask.setDeadline(777);
		testTask.setCompleted(false);
		
		controller.add(testTask);
	}
	
}