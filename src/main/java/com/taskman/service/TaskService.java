package com.taskman.service;

import com.taskman.repository.*;
import com.taskman.repository.entity.Task;
import org.springframework.stereotype.*;

import java.util.Map;
import java.util.List;

@Service
public class TaskService {

    private final ITaskRepository taskDao;

    public TaskService(ITaskRepository taskDao) {
        this.taskDao = taskDao;
    }

    /**
     * Persists new task
     *
     * @param task
     */
    public Task add(Task task) {
        return taskDao.create(task);
    }

    /**
     * Завершить задачу
     *
     * @param id
     */
    public void complete(Integer id) {
        taskDao.complete(id);
    }

    /**
     * Перечислить задачи
     *
     * @return
     */
    public List findAll() {
        return taskDao.findAll();
    }

    /**
     * Найти задачи по части описания
     *
     * @param query
     * @return
     */
    public List find(String query) {
        return taskDao.find(query);
    }
}