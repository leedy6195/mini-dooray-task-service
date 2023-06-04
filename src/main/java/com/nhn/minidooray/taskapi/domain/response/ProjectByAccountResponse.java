package com.nhn.minidooray.taskapi.domain.response;

import com.nhn.minidooray.taskapi.enumerate.ProjectStateType;
import lombok.Data;

@Data
public class ProjectByAccountResponse {
    private String accountId;
    private Long ProjectId;
    private String projectName;
    private ProjectStateType projectState;
}
