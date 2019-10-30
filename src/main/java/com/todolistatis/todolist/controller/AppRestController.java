package com.todolistatis.todolist.controller;

import java.util.List;

import com.todolistatis.todolist.model.Task;
import com.todolistatis.todolist.model.User;
import com.todolistatis.todolist.service.TaskService;
import com.todolistatis.todolist.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/custom")
public class AppRestController {

    TaskService taskService;
    UserService userService;

    @Autowired
    public AppRestController(TaskService theTaskService, UserService theUserService) {
        taskService = theTaskService;
        userService = theUserService;
    }

    @GetMapping("/tasks")
    public Task newTask(@RequestParam(value = "statusId", required = true, defaultValue = "true") int id) {

        Task theTask = taskService.getLastWhereStatusId(id);
        // theTask.setStatus(null);

        return theTask;
    }

    @PatchMapping("/tasks")
    public void saveTask(@RequestBody List<Task> tasks) {

        taskService.updatePosition(tasks);
    }

    @GetMapping("/users/exists")
    public Boolean checkIfUserExists(@RequestParam(name = "username", required = true) String username) {


        boolean exists = true;

        try {
            User temp = userService.getOne(username);
            System.out.println(temp);
        } catch (Exception e) {
            exists = false;
        }

        return exists;
    }


}
