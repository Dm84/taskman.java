package com.test.domain;

import javax.persistence.*;

@Entity
@Table(name="task")
public class Task {
	
    @Id
    @Column(name="id")
    @GeneratedValue
    private Integer id = 0;	
	
    @Column(name="description")
	private String description;
    
    @Column(name="deadline")
    private java.sql.Timestamp deadline;
    
    @Column(name="completed")
    private boolean completed = false;
    
    public Task() {
    	java.util.Date now = new java.util.Date();
    	deadline = new java.sql.Timestamp(now.getTime());    	
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public java.sql.Timestamp getDeadline() {
		return deadline;
	}

	public void setDeadline(java.sql.Timestamp deadline) {
		this.deadline = deadline;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	} 
	
}