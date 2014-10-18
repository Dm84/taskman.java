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

	/**
	 * Завершить задачу
	 * @param id
	 */
	public void complete(Integer id) {
		taskDao.complete(id);
	}

	/**
	 * Перечислить задачи
	 * @return
	 */
	public Map<Integer, Task> listAll() {
		return taskDao.listAll();
	}

	/**
	 * Найти задачи по части описания
	 * @param query
	 * @return
	 */
	public Map<Integer, Task> find(String query) {
		return taskDao.find(query);
	}
}