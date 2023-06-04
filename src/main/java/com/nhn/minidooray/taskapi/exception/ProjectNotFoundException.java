package com.nhn.minidooray.taskapi.exception;

public class ProjectNotFoundException extends ApiException {
    public ProjectNotFoundException(String message) {
        super(message);
    }
}
