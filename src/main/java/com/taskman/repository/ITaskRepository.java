package com.taskman.repository;

import java.util.List;

import com.taskman.repository.entity.Task;

public interface ITaskRepository {

    Task create(Task task);

    void complete(Integer id);

    List findAll();

    List find(String query);
}

