package com.taskman.dao;

import java.util.List;

import com.taskman.domain.Task;

public interface ITaskDao {

    Task create(Task task);

    void complete(Integer id);

    List findAll();

    List find(String query);
}

