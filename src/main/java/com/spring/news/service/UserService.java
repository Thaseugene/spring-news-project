package com.spring.news.service;

import com.spring.news.model.User;
import com.spring.news.service.exception.AlreadyExistsException;
import com.spring.news.service.exception.IncorrectLoginException;
import com.spring.news.service.exception.UserServiceException;

public interface UserService {

    void addNewUser(User user) throws UserServiceException, AlreadyExistsException;

    User getUserByLoginAndPass(String login, String password) throws UserServiceException, IncorrectLoginException;

}
