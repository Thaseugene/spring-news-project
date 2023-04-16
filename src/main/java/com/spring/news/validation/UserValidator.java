package com.spring.news.validation;

import com.spring.news.model.user.User;
import com.spring.news.service.UserService;
import com.spring.news.service.exception.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.Optional;

@Component
public class UserValidator implements Validator {

    public static final String USERNAME_FIELD_NAME = "username";
    public static final String PASSWORD_FIELD_NAME = "password";
    public static final String ALREADY_EXISTS_MESSAGE = "This login already exists";
    public static final String PASSWORDS_ARE_NOT_EQUALS = "Passwords are not equals";
    public static final String DEFAULT_MESSAGE = "Something wrong, please try later";
    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        try {
            Optional<User> userOptional = userService.takeUserByUsername(user.getUsername());
            if (userOptional.isPresent()) {
                errors.rejectValue(USERNAME_FIELD_NAME,"", ALREADY_EXISTS_MESSAGE);
            } else if (!Objects.equals(user.getPassword(), user.getConfirmPassword())) {
                errors.rejectValue(PASSWORD_FIELD_NAME,"", PASSWORDS_ARE_NOT_EQUALS);
            }
        } catch (UserServiceException e) {
            errors.rejectValue(USERNAME_FIELD_NAME,"", DEFAULT_MESSAGE);
        }
    }
}
