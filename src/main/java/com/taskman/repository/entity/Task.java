package com.taskman.repository.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "task")
public class Task implements com.taskman.service.Task {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id = 0;

    @Column(name = "description", nullable = false)
    @Size(min = 1)
    private String description;

    @Column(name = "deadline", nullable = false)
    @Future
    private java.util.Date deadline;

    @Column(name = "completed")
    private boolean completed = false;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public java.util.Date getDeadline() {
        return deadline;
    }

    @Override
    public void setDeadline(long deadline) {
        this.deadline = new java.sql.Timestamp(deadline);
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}