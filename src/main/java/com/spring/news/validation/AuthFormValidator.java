package com.spring.news.validation;

import java.util.ArrayList;
import java.util.List;

public class AuthFormValidator implements Validator {

    private final String login;
    private final String password;

    public AuthFormValidator(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public void validate() throws FormValidationException {
        List<String> errors = new ArrayList<>();
        if (login == null || login.trim().isEmpty()) {
            errors.add("Login field is empty");
        }
        if (password == null || password.trim().isEmpty()) {
            errors.add("Password field is empty");
        }
        if (!errors.isEmpty()) {
            throw new FormValidationException(String.join("; ", errors));
        }
    }
}
