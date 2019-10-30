package com.todolistatis.todolist.service;

import java.util.List;

import com.todolistatis.todolist.model.Task;
import com.todolistatis.todolist.model.TaskStatus;
import com.todolistatis.todolist.model.User;


public interface ITaskService {

    Task getOne(int id);

    List<Task> findAllTasks();

    void save(Task task);

    Task saveAndFlush(Task task);

    void delete(int id);

    List<TaskStatus> findAllTasksStatusByUser();

    Task getLastWhereStatusId(int StatusId);

    User getOneUser(String username);

    void updatePosition(List<Task> tasks);

}
