package com.nhn.minidooray.taskapi.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectByAccountResponse {
    private String accountId;
    private Long projectId;
    private String projectName;
    private String projectStateCode;
}
