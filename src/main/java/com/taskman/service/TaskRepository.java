package com.taskman.service;

import java.util.Collection;

public interface TaskRepository {

    Task create(Task task);

    void complete(Integer id);

    Collection<? extends Task> findAll();

    Collection<? extends Task> find(String query);
}

