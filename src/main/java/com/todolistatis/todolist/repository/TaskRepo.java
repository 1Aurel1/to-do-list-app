package com.todolistatis.todolist.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.todolistatis.todolist.model.Task;


public interface TaskRepo extends JpaRepository<Task, Integer> {

    // @Query("select t from #{#entityName} t where t.status.id = ?1 and t.user.username = ?#{principal.username} ")
    // public List<Task> findAllTasksByStatusAndUser(Integer status);

    @Query("select t from #{#entityName} t where t.status.id = ?1")
    public List<Task> getLastWhereStatusId(int statusId);

    // List<Task> tasks findAllTasksBy

}
