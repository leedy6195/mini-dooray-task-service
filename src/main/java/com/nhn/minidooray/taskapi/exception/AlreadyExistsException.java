package com.nhn.minidooray.taskapi.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends ApiException {
    private static final String SUFFIX = " already exists";

    public AlreadyExistsException(String target) {
        super(target + SUFFIX, HttpStatus.CONFLICT.value());
    }
}