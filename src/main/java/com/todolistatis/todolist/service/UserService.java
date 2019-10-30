package com.todolistatis.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todolistatis.todolist.model.Authority;
import com.todolistatis.todolist.model.User;
import com.todolistatis.todolist.repository.AuthorityRepo;
import com.todolistatis.todolist.repository.UserRepo;

@Service
public class UserService implements IUserService {

    UserRepo userRepo;
    AuthorityRepo authDao;

    @Autowired
    public UserService(UserRepo theUserRepo, AuthorityRepo theAuthorityRepo) {
        userRepo = theUserRepo;
        authDao = theAuthorityRepo;
    }

    @Override
    public User getOne(String username) {

        return userRepo.getOne(username);
    }

    @Override
    public void save(User user) {
        userRepo.save(user);
        Authority auth = new Authority(user.getUsername(), "ROLE_USER");
        authDao.save(auth);
    }

    @Override
    public void delete(String username) {

        userRepo.delete(getOne(username));

    }

    @Override
    public User findByEmailIgnoreCase(String email) {
        return userRepo.findByEmailIgnoreCase(email);
    }

}
