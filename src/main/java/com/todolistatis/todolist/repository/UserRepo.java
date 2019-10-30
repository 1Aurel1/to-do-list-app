package com.todolistatis.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todolistatis.todolist.model.User;


public interface UserRepo extends JpaRepository<User, String> {

    public User findByEmailIgnoreCase(String email);

    @Override
    void delete(User user);

}
