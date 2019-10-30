package com.todolistatis.todolist.service;


import com.todolistatis.todolist.model.User;

public interface IUserService {


    User getOne(String username);

    void save(User user);

    void delete(String username);

    User findByEmailIgnoreCase(String email);


}
