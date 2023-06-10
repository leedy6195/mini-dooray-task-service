package com.nhn.minidooray.taskapi.domain.request;

import lombok.Data;

@Data
public class CommentCreateRequest {
    private Long parentId;
    private Long taskId;
    private String writerId;
    private String content;
}
