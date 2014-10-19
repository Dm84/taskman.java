package com.test.dao;

import java.util.List;
import com.test.domain.Task;

public interface ITaskDao {	
	
	public int create(Task task);
	public void complete(Integer id);
	
	public List findAll();
	public List find(String query);
}

