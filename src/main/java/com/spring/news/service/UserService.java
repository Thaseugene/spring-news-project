package com.spring.news.service;

import com.spring.news.model.User;

public interface UserService {

    void addNewUser(UserForm userForm) throws UserServiceException, AlreadyExistsException;

    User getUserByLoginAndPass(String login, String password) throws UserServiceException, IncorrectLoginException;

}
