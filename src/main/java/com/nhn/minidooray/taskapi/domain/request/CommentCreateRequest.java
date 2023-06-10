package com.nhn.minidooray.taskapi.domain.request;

import lombok.Data;

@Data
public class CommentCreateRequest {
    private Long parentId;
    private String writerId;
    private String content;
}
