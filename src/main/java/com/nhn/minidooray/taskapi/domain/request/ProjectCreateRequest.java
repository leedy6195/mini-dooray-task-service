package com.nhn.minidooray.taskapi.domain.request;

import com.nhn.minidooray.taskapi.entity.ProjectEntity;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ProjectCreateRequest {
    @NotNull
    @NotEmpty
    private String accountId;
    @NotNull
    @NotEmpty
    private String projectName;
}