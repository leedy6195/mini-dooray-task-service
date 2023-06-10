package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.CommentCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.CommentUpdateRequest;

public interface CommentService {
    Long createComment(CommentCreateRequest commentCreateRequest);
    Long updateComment(Long commentId, CommentUpdateRequest commentUpdateRequest);
    void deleteComment(Long commentId);
}
