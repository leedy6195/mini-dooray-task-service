package com.nhn.minidooray.taskapi.exception;

import org.springframework.validation.BindingResult;

public class ValidationFailedException extends ApiException {
    public ValidationFailedException(String message) {
        super(message);
    }

    public ValidationFailedException(BindingResult bindingResult) {
        super(bindingResult.getFieldError().getDefaultMessage());
    }
}
