package com.spring.news.service.impl;

import com.spring.news.model.user.Role;
import com.spring.news.model.user.User;
import com.spring.news.repository.UserRepository;
import com.spring.news.repository.exception.UserRepositoryException;
import com.spring.news.service.UserService;
import com.spring.news.service.exception.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    public void addNewUser(User user) throws UserServiceException {
        try {
                String encodedPassword = passwordEncoder.encode(user.getPassword());

                user.setPassword(encodedPassword);
                user.setRole(new Role(2));
                user.setActive(true);
                user.getUserInfo().setRegisterDate(new Date());

                userRepository.addNewUser(user);

        } catch (UserRepositoryException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    @Transactional
    public Optional<User> takeUserByUsername(String username) throws UserServiceException {
        try {
            return userRepository.takeUserByLogin(username);
        } catch (UserRepositoryException e) {
            throw new UserServiceException(e);
        }
    }
}
