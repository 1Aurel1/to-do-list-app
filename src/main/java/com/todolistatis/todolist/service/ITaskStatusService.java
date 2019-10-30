package com.todolistatis.todolist.service;

import java.util.List;

import com.todolistatis.todolist.model.TaskStatus;

public interface ITaskStatusService {

    TaskStatus getOne(int id);

    List<TaskStatus> findAllTasksStatusByUser();

    void save(TaskStatus taskStatus);

    TaskStatus saveAndFlush(TaskStatus taskStatus);

    void delete(int id);
}
