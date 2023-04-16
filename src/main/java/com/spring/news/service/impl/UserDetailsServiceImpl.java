package com.spring.news.service.impl;

import com.spring.news.model.user.User;
import com.spring.news.repository.UserRepository;
import com.spring.news.repository.exception.UserRepositoryException;
import com.spring.news.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<User> user = userRepository.takeUserByLogin(username);
            if (user.isPresent()) {
                return new UserDetailsImpl(user.get());
            } else {
                throw new UsernameNotFoundException("User not found");
            }
        } catch (UserRepositoryException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }

    }
}
