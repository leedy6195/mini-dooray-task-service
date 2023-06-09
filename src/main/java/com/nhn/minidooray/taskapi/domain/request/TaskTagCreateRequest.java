package com.nhn.minidooray.taskapi.domain.request;

import lombok.Data;

@Data
public class TaskTagCreateRequest {
    private Long taskId;
    private Long tagId;
}
