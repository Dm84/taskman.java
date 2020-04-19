package com.taskman.service;

import com.taskman.repository.*;
import com.taskman.repository.entity.Task;
import org.springframework.stereotype.*;

import java.util.Map;
import java.util.List;

@Service
public class TaskService {

    private final ITaskRepository taskRepository;

    public TaskService(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Persists new task
     *
     * @param task
     */
    public Task add(Task task) {
        return taskRepository.create(task);
    }

    /**
     * Завершить задачу
     *
     * @param id
     */
    public void complete(Integer id) {
        taskRepository.complete(id);
    }

    /**
     * Перечислить задачи
     *
     * @return
     */
    public List findAll() {
        return taskRepository.findAll();
    }

    /**
     * Найти задачи по части описания
     *
     * @param query
     * @return
     */
    public List find(String query) {
        return taskRepository.find(query);
    }
}