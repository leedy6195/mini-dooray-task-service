package com.nhn.minidooray.taskapi.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
public class ProjectCreateResponse {
    private Long projectId;
    @Builder
    public ProjectCreateResponse(Long projectId) {
        this.projectId = projectId;
    }
}
