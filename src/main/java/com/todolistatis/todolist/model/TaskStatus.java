package com.todolistatis.todolist.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotBlank;


@Entity(name = "task_status")
public class TaskStatus implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column
    @NotBlank(message = "The status name can not be blank!")
    private String status;

    @Column
    private int position;

    @OneToMany(mappedBy = "status", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy(value = "position ASC")
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public TaskStatus() {
    }

    public TaskStatus(int id, String status, int position, List<Task> tasks, User user) {
        this.id = id;
        this.status = status;
        this.position = position;
        this.tasks = tasks;
        this.user = user;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TaskStatus id(int id) {
        this.id = id;
        return this;
    }

    public TaskStatus status(String status) {
        this.status = status;
        return this;
    }

    public TaskStatus position(int position) {
        this.position = position;
        return this;
    }

    public TaskStatus tasks(List<Task> tasks) {
        this.tasks = tasks;
        return this;
    }

    public TaskStatus user(User user) {
        this.user = user;
        return this;
    }


    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", status='" + getStatus() + "'" +
                ", position='" + getPosition() + "'" +
                ", tasks='" + getTasks() + "'" +
                ", user='" + getUser() + "'" +
                "}";
    }


}
