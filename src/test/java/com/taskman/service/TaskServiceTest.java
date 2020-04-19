package com.taskman.service;

import java.sql.Timestamp;

import org.junit.Test;
import org.junit.Assert;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taskman.repository.entity.Task;

public class TaskServiceTest {
	
	final private int testId = 1;
	final private long deadline = 1445299200000l;
	
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
		testTask.setDeadline(deadline);
		
		Task newTask = service.add(testTask);		
		Assert.assertNotEquals(newTask.getId(), new Integer(0));
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