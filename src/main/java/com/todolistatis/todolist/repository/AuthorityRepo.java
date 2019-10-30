package com.todolistatis.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todolistatis.todolist.model.Authority;


public interface AuthorityRepo extends JpaRepository<Authority, String> {

}
