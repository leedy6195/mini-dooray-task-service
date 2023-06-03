package com.nhn.minidooray.taskapi.domain.request;

import lombok.Data;

@Data
public class ProjectCreateRequest {
    private String accountId;
    private String projectName;
}