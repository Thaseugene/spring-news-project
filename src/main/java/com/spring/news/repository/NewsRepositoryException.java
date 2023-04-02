package com.spring.news.repository;

public class NewsRepositoryException extends Exception{

    private static final long serialVersionUID = 1L;

    public NewsRepositoryException() {
        super();
    }

    public NewsRepositoryException(String message) {
        super(message);
    }

    public NewsRepositoryException(Exception e) {
        super(e);
    }

    public NewsRepositoryException(String message, Exception e) {
        super(message, e);
    }


}
