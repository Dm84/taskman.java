package com.test.service;

import com.test.dao.*;
import com.test.domain.*;

import org.springframework.stereotype.*;

import java.util.Map;
import java.util.List;

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
	public List findAll() {
		return taskDao.findAll();
	}

	/**
	 * Найти задачи по части описания
	 * @param query
	 * @return
	 */
	public List find(String query) {
		return taskDao.find(query);
	}
}