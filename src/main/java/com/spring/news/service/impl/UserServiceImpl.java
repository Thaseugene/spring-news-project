package com.spring.news.service.impl;

import com.spring.news.model.User;
import com.spring.news.repository.UserRepository;
import com.spring.news.repository.UserRepositoryException;
import com.spring.news.service.exception.AlreadyExistsException;
import com.spring.news.service.exception.IncorrectLoginException;
import com.spring.news.service.UserService;
import com.spring.news.service.exception.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void addNewUser(User user) throws UserServiceException, AlreadyExistsException {
        try {
            Optional<User> userOptional = userRepository.takeUserByLogin(user.getLogin());
            if (!userOptional.isPresent()) {
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
                userRepository.addNewUser(user);
            } else {
                throw new AlreadyExistsException("User with this login already exists");
            }
        } catch (UserRepositoryException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    @Transactional
    public User getUserByLoginAndPass(String login, String password) throws UserServiceException, IncorrectLoginException {
        try {
            Optional<User> userOptional = userRepository.takeUserByLogin(login);
            if (userOptional.isPresent()) {
                if(passwordEncoder.matches(password,userOptional.get().getPassword())) {
                    return userOptional.get();
                } else {
                    throw new IncorrectLoginException("Incorrect login or password");
                }
            } else {
                throw new IncorrectLoginException("Incorrect login or password");
            }
        } catch (UserRepositoryException e) {
            throw new UserServiceException(e);
        }
    }
}
