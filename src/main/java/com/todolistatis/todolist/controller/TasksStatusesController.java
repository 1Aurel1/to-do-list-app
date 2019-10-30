package com.todolistatis.todolist.controller;


import javax.validation.Valid;

import com.todolistatis.todolist.model.TaskStatus;
import com.todolistatis.todolist.service.TaskStatusService;
import com.todolistatis.todolist.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/tasks/statuses")
public class TasksStatusesController {

    private final static String PREFIX = "task_statuses/";

    TaskStatusService statusService;
    UserService userService;

    @Autowired
    public TasksStatusesController(TaskStatusService theStatusService, UserService theUserService) {

        statusService = theStatusService;
        userService = theUserService;

    }

    @GetMapping("")
    public String index(Model theModel) {

        theModel.addAttribute("title", "Statuses");
        theModel.addAttribute("statuses", statusService.findAllTasksStatusByUser());

        return PREFIX + "index";

    }

    @GetMapping("/new")
    public String newTaskStatus(Model theModel, @ModelAttribute(name = "errors") Object errors) {

        theModel.addAttribute("title", "New Status");
        theModel.addAttribute("taskStatus", new TaskStatus());

        System.err.println(errors);

        if (errors != null) {
            theModel.addAttribute("errors", errors);
        }

        return PREFIX + "new";
    }


    @PostMapping("/save")
    public String saveTaskStatus(@Valid @ModelAttribute("taskStatus") TaskStatus status,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes
    ) {

        if (result.hasErrors()) {

            System.err.println(result.getAllErrors());
            redirectAttributes.addFlashAttribute("errors", result.getAllErrors());

            return "redirect:/tasks/statuses/new";
        }

        final Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (user instanceof UserDetails) {
            username = ((UserDetails) user).getUsername();
        } else {
            username = user.toString();
        }

        status.setUser(userService.getOne(username));

        status.setPosition(999);

        statusService.saveAndFlush(status);

        return "redirect:/tasks/statuses";
    }


}
