package com.nhn.minidooray.taskapi.domain.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentByTaskResponse {
    private Long taskId;
    private Long commentId;
    private String commentWriter;
    private String commentContent;
    private LocalDateTime commentCreateAt;
    private Long parentCommentId;

    public CommentByTaskResponse(Long taskId, Long commentId, String commentWriter, String commentContent, LocalDateTime commentCreateAt, Long parentCommentId) {
        this.taskId = taskId;
        this.commentId = commentId;
        this.commentWriter = commentWriter;
        this.commentContent = commentContent;
        this.commentCreateAt = commentCreateAt;
        this.parentCommentId = parentCommentId;
    }
}
