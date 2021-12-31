package com.taskman.service;

import java.util.Collection;

public interface TaskRepository {

    /**
     * Помечает задачу как завершенную
     */
    Task create(Task task);

    /**
     * Сохраняет новую задачу
     */
    void complete(Integer id);

    /**
     * Возвращает список всех задач
     *
     * @return get all tasks having
     */
    Collection<? extends Task> findAll();

    /**
     * Ищет задачи по части описания
     *
     * @param entry entry that description contains
     * @return tasks matched string
     */
    Collection<? extends Task> find(String entry);
}

