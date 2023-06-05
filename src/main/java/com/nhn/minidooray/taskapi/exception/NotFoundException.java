package com.nhn.minidooray.taskapi.exception;


import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {
    private static final String SUFFIX = " not found";

    public NotFoundException(String target) {
        super(target + SUFFIX, HttpStatus.NOT_FOUND.value());
    }
}
