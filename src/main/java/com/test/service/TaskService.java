package com.test.service;

import javax.transaction.Transactional;

import com.test.dao.*;
import com.test.domain.*;

import org.springframework.stereotype.*;

@Service
public class TaskService {
	
	private ITaskDao taskDao;
	
	public TaskService(ITaskDao taskDao) {
		this.taskDao = taskDao;
	}
	
	/**
	 * Создает и сохраняет задание
	 * @param task
	 */
	@Transactional
	public void create(Task task) {
		taskDao.create(task);
	}
}