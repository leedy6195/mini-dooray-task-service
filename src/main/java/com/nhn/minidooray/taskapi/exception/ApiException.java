package com.nhn.minidooray.taskapi.exception;

import lombok.Getter;

import javax.management.InstanceAlreadyExistsException;

@Getter
public class ApiException extends RuntimeException {
    private final int statusCode;

    public ApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
