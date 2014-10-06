package com.test.service;

import com.test.dao.*;
import com.test.domain.*;

import org.springframework.stereotype.*;

import java.util.Map;

@Service
public class TaskService {

	private ITaskDao taskDao;

	public TaskService(ITaskDao taskDao) {
		this.taskDao = taskDao;
	}

	/**
	 * Persists new task
	 * 
	 * @param task
	 */
	public int add(Task task) {
		return taskDao.create(task);
	}

	public void complete(Integer id) {
		taskDao.complete(id);
	}

	public Map<Integer, Task> listAll() {
		return taskDao.listAll();
	}
}