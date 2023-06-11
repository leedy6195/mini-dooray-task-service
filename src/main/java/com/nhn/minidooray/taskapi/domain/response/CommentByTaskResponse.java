package com.nhn.minidooray.taskapi.domain.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class CommentByTaskResponse {
    private Long taskId;
    private Long commentId;
    private String commentWriter;
    private String commentContent;
    private LocalDate commentCreateAt;
    private Long parentCommentId;
    private List<CommentByTaskResponse> children;

    public CommentByTaskResponse(Long taskId, Long commentId, String commentWriter, String commentContent, LocalDate commentCreateAt, Long parentCommentId, List<CommentByTaskResponse> children) {
        this.taskId = taskId;
        this.commentId = commentId;
        this.commentWriter = commentWriter;
        this.commentContent = commentContent;
        this.commentCreateAt = commentCreateAt;
        this.parentCommentId = parentCommentId;
        this.children = children;
    }
}
