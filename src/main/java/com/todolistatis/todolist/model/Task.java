package com.todolistatis.todolist.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;


@Entity(name = "tasks")
public class Task implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final static String[] PRIORITY_LIST = {"low", "medium", "high"};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column
    @NotBlank(message = "The  task name can not be blank!")
    private String name;

    @Column
    String description;

    @Column
    private Integer priority;

    @Column
    private int position;

    @ManyToOne
    @JoinColumn(name = "taskStatus_id")
    private TaskStatus status;


    public Task() {
    }

    public Task(int id, String name, String description, Integer priority, int position, TaskStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.position = position;
        this.status = status;
    }

    public static String[] getPRIORITY_LIST() {
        return PRIORITY_LIST;
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getStatus() {
        return this.status.getId();
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }


    public Task id(int id) {
        this.id = id;
        return this;
    }

    public Task name(String name) {
        this.name = name;
        return this;
    }

    public Task description(String description) {
        this.description = description;
        return this;
    }

    public Task priority(Integer priority) {
        this.priority = priority;
        return this;
    }


    public Task status(TaskStatus status) {
        this.status = status;
        return this;
    }


    @Override
    public String toString() {
        return "{" +
                " PRIORITY_LIST='" + getPRIORITY_LIST() + "'" +
                ", id='" + getId() + "'" +
                ", name='" + getName() + "'" +
                ", description='" + getDescription() + "'" +
                ", priority='" + getPriority() + "'" +
                ", orderNr='" + getPosition() + "'" +
                ", status='" + getStatus() + "'" +
                "}";
    }


}
