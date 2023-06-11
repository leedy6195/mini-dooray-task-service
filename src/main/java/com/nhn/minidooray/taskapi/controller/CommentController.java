package com.nhn.minidooray.taskapi.controller;

import com.nhn.minidooray.taskapi.domain.request.CommentCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.CommentUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.CommentByTaskResponse;
import com.nhn.minidooray.taskapi.domain.response.CommonResponse;
import com.nhn.minidooray.taskapi.domain.response.ResultResponse;
import com.nhn.minidooray.taskapi.exception.ValidationFailedException;
import com.nhn.minidooray.taskapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.taskapi.requestmapping.prefix}")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("${com.nhn.minidooray.taskapi.requestmapping.create-comment}")
    public ResultResponse<CommonResponse> createComment(
            @PathVariable("taskId") Long taskId,
            @RequestBody @Valid CommentCreateRequest commentCreateRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        Long commentId = commentService.createComment(taskId, commentCreateRequest);
        return ResultResponse.created(List.of(CommonResponse.builder().id(commentId).build()));
    }

    @PutMapping("${com.nhn.minidooray.taskapi.requestmapping.update-comment}")
    public ResultResponse<CommonResponse> updateComment(
            @PathVariable("taskId") Long taskId,
            @PathVariable("commentId") Long commentId,
            @RequestBody @Valid CommentUpdateRequest commentUpdateRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        commentService.updateComment(commentId, commentUpdateRequest);
        return ResultResponse.updated(List.of(CommonResponse.builder().id(commentId).build()));
    }

    @DeleteMapping("${com.nhn.minidooray.taskapi.requestmapping.delete-comment}")
    public ResultResponse<Void> deleteComment(
            @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return ResultResponse.deleted(null);
    }

    @GetMapping("${com.nhn.minidooray.taskapi.requestmapping.get-comments}")
    public ResultResponse<Page<CommentByTaskResponse>> getComments(
            @PathVariable("taskId") Long taskId, Pageable pageable) {
        Page<CommentByTaskResponse> comments = commentService.getCommentsByTaskId(taskId, pageable);
        return ResultResponse.fetched(List.of(comments));
    }
}
