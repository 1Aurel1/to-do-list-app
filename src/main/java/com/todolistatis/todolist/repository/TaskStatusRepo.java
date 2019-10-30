package com.todolistatis.todolist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.todolistatis.todolist.model.TaskStatus;


public interface TaskStatusRepo extends JpaRepository<TaskStatus, Integer> {

    @Query("select s from #{#entityName} s where s.user.id = ?#{principal.username} order by s.position")
    public List<TaskStatus> findAllTasksStatusByUser();

}
