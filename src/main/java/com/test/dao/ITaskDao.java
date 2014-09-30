package com.test.dao;

import java.util.*;

import com.test.domain.Task;

public interface ITaskDao {	
	
	public void create(Task task);
	public void complete(Integer id);
	
	public Map<Integer, Task> listAll();	
}

