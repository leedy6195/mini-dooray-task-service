package com.nhn.minidooray.taskapi.domain.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectAccountCreateRequest {
    private String accountId;
    private String authorityCode;
}
