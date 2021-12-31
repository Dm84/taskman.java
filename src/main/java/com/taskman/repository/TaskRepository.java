package com.taskman.repository;

import com.taskman.service.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Collection;

@Repository
@EnableTransactionManagement
class TaskRepository implements com.taskman.service.TaskRepository {

    private final SessionFactory factory;

    TaskRepository(SessionFactory factory) {
        this.factory = factory;
    }

    private Session getSession() {
        return this.factory.getCurrentSession();
    }

    public void complete(Integer id) {
        var task = getSession().byId(Task.class).getReference(id);
        task.setCompleted(true);
        getSession().update(task);
    }

    public Task create(Task task) {
        getSession().save(task);
        return task;
    }

    public Collection<Task> findAll() {
        var builder = getSession().getCriteriaBuilder();
        var query = builder.createQuery(Task.class);
        var all = query.select(query.from(Task.class));
        return getSession().createQuery(all).getResultList();
    }

    public Collection<Task> find(String entry) {
        var builder = getSession().getCriteriaBuilder();
        var query = builder.createQuery(Task.class);
        var root = query.from(Task.class);
        var all = query
                .select(root)
                .where(builder.like(root.get("description"), "%" + entry + "%"));

        return getSession().createQuery(all).getResultList();
    }
}