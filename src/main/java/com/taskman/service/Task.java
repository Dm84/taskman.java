package com.taskman.service;

public interface Task {
    Integer getId();

    void setId(Integer id);

    String getDescription();

    void setDescription(String description);

    java.util.Date getDeadline();

    void setDeadline(long deadline);

    boolean isCompleted();

    void setCompleted(boolean completed);
}
