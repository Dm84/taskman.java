package com.taskman.service;

import com.taskman.repository.entity.Task;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TaskService {

    private final ITaskRepository taskRepository;

    public TaskService(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Persists new task
     *
     * @param task input task
     */
    public Task add(Task task) {
        return taskRepository.create(task);
    }

    /**
     * Завершить задачу
     *
     * @param id id of task
     */
    public void complete(Integer id) {
        taskRepository.complete(id);
    }

    /**
     * Перечислить задачи
     *
     * @return all of task what we have
     */
    public Collection<Task> findAll() {
        return taskRepository.findAll();
    }

    /**
     * Найти задачи по части описания
     *
     * @param query description subsequence for search
     * @return item list
     */
    public Collection<Task> find(String query) {
        return taskRepository.find(query);
    }
}