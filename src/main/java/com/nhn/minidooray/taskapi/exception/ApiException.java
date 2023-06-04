package com.nhn.minidooray.taskapi.exception;

import javax.management.InstanceAlreadyExistsException;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
