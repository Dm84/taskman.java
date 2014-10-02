package com.test.dao;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.*;
import org.hibernate.SessionFactory;
import org.hibernate.Session;

import com.test.domain.Task;
import org.springframework.transaction.annotation.*;

@Repository
@EnableTransactionManagement
class TaskDao implements ITaskDao {
	
	private SessionFactory factory;
	
	TaskDao(SessionFactory factory) {
		this.factory = factory;
	}
	
	private Session getSession() {
		return this.factory.getCurrentSession();
	}
	
	public void complete(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
	public void create(Task task) {
		Session session = getSession();
		getSession().save(task);
	}
	
	public Map<Integer, Task> listAll() {
		// TODO Auto-generated method stub
		return null;
	}
}