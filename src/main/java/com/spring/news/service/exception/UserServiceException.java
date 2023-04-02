package com.spring.news.service.exception;

public class UserServiceException extends Exception{

    private static final long serialVersionUID = 1L;

    public UserServiceException() {
        super();
    }

    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(Exception e) {
        super(e);
    }

    public UserServiceException(String message, Exception e) {
        super(message, e);
    }
}
