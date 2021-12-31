package com.taskman.repository;

import com.taskman.repository.entity.Task;
import com.taskman.service.ITaskRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

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
        return taskCollection.stream().collect(Collectors.toMap(Task::getId, task -> task));
    }

    /**
     * Возвращает список всех задач
     *
     * @return get all tasks having
     */
    public Collection<Task> findAll() {
        var builder = getSession().getCriteriaBuilder();
        var query = builder.createQuery(Task.class);
        var all = query.select(query.from(Task.class));
        return getSession().createQuery(all).getResultList();
    }

    /**
     * Ищет задачи по части описания
     *
     * @param entry entry that description contains
     * @return tasks matched string
     */
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