package com.spring.news.repository;

import com.spring.news.model.user.User;
import com.spring.news.repository.exception.UserRepositoryException;

import java.util.Optional;

public interface UserRepository {

    Optional<User> takeUserByLogin(String login) throws UserRepositoryException;
    void addNewUser(User user) throws UserRepositoryException;

    void updateUser(User user) throws UserRepositoryException;

    void deleteUser(User user) throws UserRepositoryException;

}
