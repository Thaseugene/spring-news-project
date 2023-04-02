package com.spring.news.service.exception;

public class NewsServiceException extends Exception{

    private static final long serialVersionUID = 1L;

    public NewsServiceException() {
        super();
    }

    public NewsServiceException(String message) {
        super(message);
    }

    public NewsServiceException(Exception e) {
        super(e);
    }

    public NewsServiceException(String message, Exception e) {
        super(message, e);
    }
}
