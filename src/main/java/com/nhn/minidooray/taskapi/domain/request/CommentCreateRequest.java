package com.nhn.minidooray.taskapi.domain.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class CommentCreateRequest {
    private Long parentId;
    private String writerId;
    private String content;
}
