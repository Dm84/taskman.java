package com.taskman.service;

import java.util.Collection;

import com.taskman.repository.entity.Task;

public interface ITaskRepository {

    Task create(Task task);

    void complete(Integer id);

    Collection<Task> findAll();

    Collection<Task> find(String query);
}

