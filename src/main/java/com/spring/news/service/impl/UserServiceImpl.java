package com.spring.news.service.impl;

import com.spring.news.model.Role;
import com.spring.news.model.User;
import com.spring.news.repository.UserRepository;
import com.spring.news.repository.UserRepositoryException;
import com.spring.news.service.*;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final String salt = BCrypt.gensalt();
    private UserServiceImpl() {
    }


    @Override
    public void addNewUser(UserForm userForm) throws UserServiceException, AlreadyExistsException {
        try {
            if (!userRepository.checkIsLoginExists(userForm.getLogin())) {
                User user = new User(
                        (new Random()).nextInt(),
                        userForm.getLogin(),
                        BCrypt.hashpw(userForm.getPassword(), salt),
                        userForm.getEmail(),
                        Role.USER,
                        true,
                        userForm.getName(),
                        userForm.getSurname(),
                        new Date());
                userRepository.addNewUser(user);
            } else {
                throw new AlreadyExistsException("User with this login already exists");
            }
        } catch (UserRepositoryException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public User getUserByLoginAndPass(String login, String password) throws UserServiceException, IncorrectLoginException {
        try {
            int id = userRepository.takeUsersIdByLogin(login, password);
            if (id != 0) {
                return userRepository.takeUserById(id);
            } else {
                throw new IncorrectLoginException("Incorrect login or password");
            }
        } catch (UserRepositoryException e) {
            throw new UserServiceException(e);
        }
    }
}
