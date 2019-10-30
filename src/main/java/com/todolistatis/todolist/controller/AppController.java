package com.todolistatis.todolist.controller;

import com.todolistatis.todolist.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AppController {

    @Autowired
    private UserRepo userRepo;

    public AppController() {
    }

    @GetMapping("")
    public String redicretToTasks() {

        return "redirect:tasks";
    }

    @GetMapping(value = "test")
    public String getMethodName(Model theModel) {

        theModel.addAttribute("user", userRepo.getOne("reli"));

        return "users/successfulRegisteration";
    }


}
