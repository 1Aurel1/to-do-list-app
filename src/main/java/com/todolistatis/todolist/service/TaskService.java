package com.todolistatis.todolist.service;

import java.util.List;

import com.todolistatis.todolist.model.Task;
import com.todolistatis.todolist.model.TaskStatus;
import com.todolistatis.todolist.model.User;
import com.todolistatis.todolist.repository.TaskRepo;
import com.todolistatis.todolist.repository.TaskStatusRepo;
import com.todolistatis.todolist.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService implements ITaskService {

    TaskRepo taskDao;
    TaskStatusRepo status;
    UserRepo userDao;

    @Autowired
    public TaskService(TaskRepo theTaskRepo, TaskStatusRepo theStatus, UserRepo theUserRepo) {
        taskDao = theTaskRepo;
        status = theStatus;
        userDao = theUserRepo;
    }

    @Override
    public Task getOne(int id) {

        return taskDao.getOne(id);
    }

    @Override
    public List<Task> findAllTasks() {

        return taskDao.findAll();
    }

    @Override
    public void save(Task task) {

        taskDao.save(task);

    }

    @Override
    public Task saveAndFlush(Task task) {

        task = taskDao.saveAndFlush(task);


        return task;
    }

    @Override
    public void delete(int id) {

        taskDao.delete(taskDao.getOne(id));

    }

    @Override
    public List<TaskStatus> findAllTasksStatusByUser() {
        return status.findAllTasksStatusByUser();
    }

    @Override
    public User getOneUser(String username) {

        return userDao.getOne(username);
    }

    @Override
    public Task getLastWhereStatusId(int StatusId) {

        Task task = taskDao.getLastWhereStatusId(StatusId).get(0);

        return task;
    }

    @Override
    public void updatePosition(List<Task> tasks) {

        taskDao.saveAll(tasks);

    }

}
