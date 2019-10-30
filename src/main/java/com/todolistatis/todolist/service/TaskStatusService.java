package com.todolistatis.todolist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todolistatis.todolist.model.TaskStatus;
import com.todolistatis.todolist.repository.TaskStatusRepo;

@Service
public class TaskStatusService implements ITaskStatusService {

    TaskStatusRepo statusRepo;

    @Autowired
    public TaskStatusService(TaskStatusRepo theStatusRepo) {
        statusRepo = theStatusRepo;
    }

    @Override
    public TaskStatus getOne(int id) {

        return statusRepo.getOne(id);
    }

    @Override
    public List<TaskStatus> findAllTasksStatusByUser() {

        return statusRepo.findAllTasksStatusByUser();
    }

    @Override
    public void save(TaskStatus taskStatus) {

        statusRepo.save(taskStatus);

    }

    @Override
    public TaskStatus saveAndFlush(TaskStatus taskStatus) {

        return statusRepo.saveAndFlush(taskStatus);
    }

    @Override
    public void delete(int id) {

        statusRepo.delete(statusRepo.getOne(id));

    }

}
