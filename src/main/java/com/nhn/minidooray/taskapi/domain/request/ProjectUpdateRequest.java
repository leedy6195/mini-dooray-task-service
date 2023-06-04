package com.nhn.minidooray.taskapi.domain.request;

import lombok.Data;

@Data
public class ProjectUpdateRequest {
    private String projectName;
    private String projectStateCode;
}
