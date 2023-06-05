package com.nhn.minidooray.taskapi.domain.request;

import lombok.Data;

@Data
public class ProjectAccountCreateRequest {
    private String accountId;
    private String authorityCode;
}
