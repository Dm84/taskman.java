package com.taskman.repository;

import java.util.*;
import java.util.stream.Collectors;

import com.taskman.service.ITaskRepository;
import org.springframework.stereotype.*;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.*;

import com.taskman.repository.entity.Task;

@Repository
@EnableTransactionManagement
class TaskRepository implements ITaskRepository {

    private final SessionFactory factory;

    TaskRepository(SessionFactory factory) {
        this.factory = factory;
    }

    private Session getSession() {
        return this.factory.getCurrentSession();
    }

    /**
     * Помечает задачу как завершенную
     */
    public void complete(Integer id) {
        Task task = (Task) getSession().byId(Task.class).getReference(id);
        task.setCompleted(true);
        getSession().update(task);
    }

    /**
     * Сохраняет новую задачу
     */
    public Task create(Task task) {
        getSession().save(task);
        return task;
    }

    /**
     * Индексирует список задач
     *
     * @param taskCollection input collection
     * @return task index
     */
    private Map<Integer, Task> IndexList(Collection<Task> taskCollection) {
        final Map<Integer, Task> map = new HashMap<>();
        return taskCollection.stream().collect(Collectors.toMap(Task::getId, task -> task));
    }

    /**
     * Возвращает список всех задач
     *
     * @return get all tasks having
     */
    public Collection<Task> findAll() {
        return getSession().createCriteria(Task.class).list();
    }

    /**
     * Ищет задачи по части описания
     *
     * @param query input string
     * @return tasks matched string
     */
    public Collection<Task> find(String query) {
        return getSession().createCriteria(Task.class).add(
                Restrictions.like("description", "%" + query + "%")).list();
    }
}