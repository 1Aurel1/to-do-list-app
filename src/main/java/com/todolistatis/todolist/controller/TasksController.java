package com.todolistatis.todolist.controller;

import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import com.todolistatis.todolist.model.Task;
import com.todolistatis.todolist.model.TaskStatus;
import com.todolistatis.todolist.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tasks")
public class TasksController {

    private final static String PREFIX = "tasks/";

    TaskService taskService;


    @Autowired
    public TasksController(TaskService theTaskService) {

        taskService = theTaskService;

    }

    @GetMapping("")
    public String index(@ModelAttribute(name = "errors") Object errors, Model theModel) {

        List<TaskStatus> statuses = taskService.findAllTasksStatusByUser();

        theModel.addAttribute("title", "Tasks");
        theModel.addAttribute("statuses", statuses);

        if (errors != null) {
            theModel.addAttribute("errors", errors);
        }

        return PREFIX + "index";
    }

    @GetMapping("/{id}")
    public String showTask(@PathVariable(name = "id", required = true) Integer id, Model theModel) {

        theModel.addAttribute("title", "Show task");
        theModel.addAttribute("task", taskService.getOne(id));
        theModel.addAttribute("status", taskService.findAllTasksStatusByUser());

        return PREFIX + "show";
    }


    @PostMapping("/save")
    public String saveOrUpdateTask(@Valid @ModelAttribute("task") Task task, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {

            redirectAttributes.addFlashAttribute("errors", result.getAllErrors());

            return "redirect:" + PREFIX + "/";
        }

        // task.setPosition(new TaskPosition());

        taskService.saveAndFlush(task);

        return "redirect:/tasks/show/" + task.getId();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTask(@PathParam("id") int id) {

        taskService.delete(id);

        return "redircet:/tasks";
    }


}
