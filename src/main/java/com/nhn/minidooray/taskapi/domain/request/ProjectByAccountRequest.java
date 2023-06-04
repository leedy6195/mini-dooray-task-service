package com.nhn.minidooray.taskapi.domain.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ProjectByAccountRequest {
    @NotNull
    @NotEmpty
    private String accountId;
}
