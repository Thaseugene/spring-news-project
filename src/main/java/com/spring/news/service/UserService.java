package com.spring.news.service;

import com.spring.news.model.user.User;
import com.spring.news.service.exception.UserServiceException;

import java.util.Optional;

public interface UserService {

    void addNewUser(User user) throws UserServiceException;

    Optional<User> takeUserByUsername(String username) throws UserServiceException;

}
