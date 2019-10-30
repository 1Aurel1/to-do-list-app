package com.todolistatis.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todolistatis.todolist.model.ConfirmationToken;


public interface ConfTokenRepo extends JpaRepository<ConfirmationToken, String> {


    ConfirmationToken findByConfirmationToken(String confirmationToken);

}
