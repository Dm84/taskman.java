package com.test.dao;

import java.util.Map;

import com.test.domain.Task;

public interface ITaskDao {	
	
	public int create(Task task);
	public void complete(Integer id);
	
	public Map<Integer, Task> listAll();	
}

