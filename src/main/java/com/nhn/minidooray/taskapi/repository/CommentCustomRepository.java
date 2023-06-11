package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.domain.response.CommentByTaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentCustomRepository {
    Page<CommentByTaskResponse> findCommentsByTaskId(Long taskId, Pageable pageable);
}
