package com.test.service;

import java.sql.Timestamp;

import org.junit.Test;
import org.junit.Assert;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.test.domain.Task;

public class TaskServiceTest {
	
	final private int testId = 1;
	
	private ApplicationContext appContext;
	private TaskService service;
	
	public TaskServiceTest() {		
		appContext = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");		
		service = appContext.getBean("taskService", TaskService.class);   
	}
	
	@Test
	public void add() {		
		Task testTask = new Task();		
		testTask.setDescription("test desc");		
		
		int id = service.add(testTask);		
		Assert.assertNotEquals(id, 0);
	}
	
	@Test
	public void complete() {		
		service.complete(testId);
	}
	
	@Test
	public void listAll() {
		java.util.List tasks = service.findAll();		
		Assert.assertNotEquals(tasks.size(), 0);
	}
	
	@Test
	public void find() {
		java.util.List tasks = service.find("esc");		
		Assert.assertNotEquals(tasks.size(), 0);
	}
}