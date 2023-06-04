package com.nhn.minidooray.taskapi.advice;

import com.nhn.minidooray.taskapi.domain.response.ResultResponse;
import com.nhn.minidooray.taskapi.exception.ApiException;
import com.nhn.minidooray.taskapi.exception.ValidationFailedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// todo error 처리
@RestControllerAdvice
public class ApiAdvice {
    @ExceptionHandler(ValidationFailedException.class)
    public ResultResponse handleValidationFailedException(ValidationFailedException ex) {
        return ResultResponse.builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(false)
                        .resultCode(400)
                        .resultMessage(ex.getMessage())
                        .build())
                .build();
    }
}