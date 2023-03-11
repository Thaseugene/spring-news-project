package com.spring.news.validation;

public class FormValidationException extends Exception{

    private static final long serialVersionUID = 1L;

    public FormValidationException() {
        super();
    }

    public FormValidationException(String message) {
        super(message);
    }

    public FormValidationException(Exception e) {
        super(e);
    }

    public FormValidationException(String message, Exception e) {
        super(message, e);
    }
}
