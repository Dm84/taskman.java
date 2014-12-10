package com.taskman.dao;

import java.util.List;
import com.taskman.domain.Task;

public interface ITaskDao {	
	
	public Task create(Task task);
	public void complete(Integer id);
	
	public List findAll();
	public List find(String query);
}

