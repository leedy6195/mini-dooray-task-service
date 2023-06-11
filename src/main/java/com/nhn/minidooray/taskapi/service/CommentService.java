package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.CommentCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.CommentUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.CommentByTaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Long createComment(Long taskId, CommentCreateRequest commentCreateRequest);
    Long updateComment(Long commentId, CommentUpdateRequest commentUpdateRequest);
    void deleteComment(Long commentId);
    Page<CommentByTaskResponse> getCommentsByTaskId(Long taskId, Pageable pageable);
}
