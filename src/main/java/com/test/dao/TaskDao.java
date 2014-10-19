package com.test.dao;

import java.util.List;
import java.util.ListIterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.springframework.stereotype.*;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.*;

import com.test.domain.Task;

@Repository
@EnableTransactionManagement
@Transactional
class TaskDao implements ITaskDao {
	
	private SessionFactory factory;
	
	TaskDao(SessionFactory factory) {
		this.factory = factory;
	}	
	
	private Session getSession() {
		return this.factory.getCurrentSession();
	}
	
	public void complete(Integer id) {
		Task task = (Task) getSession().byId(Task.class).getReference(id);
		task.setCompleted(true);
		getSession().update(task);
	}
	
	public int create(Task task) {		
		getSession().save(task);	
		return task.getId();
	}	
	
	/**
	 * Индексирует список задач
	 * @param iter
	 * @return
	 */
	private Map<Integer, Task> IndexList(ListIterator iter) {
		Map<Integer, Task> map = new HashMap<Integer, Task>();
		while (iter.hasNext()) {			
			Object obj = iter.next();			
			if (obj instanceof Task)
			{
				Task item = (Task)obj;
				map.put(item.getId(), item);
			}			
		}		
		return map;
	}
	
	public List findAll() {		
		return getSession().createCriteria(Task.class).list();
	}	
	
	public List find(String query) {		
		return getSession().createCriteria(Task.class).add(
				Restrictions.like("description", "%" + query + "%")).list();
	}
}