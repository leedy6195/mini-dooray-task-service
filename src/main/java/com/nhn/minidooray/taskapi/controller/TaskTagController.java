package com.nhn.minidooray.taskapi.controller;

import com.nhn.minidooray.taskapi.domain.request.TaskTagCreateRequest;
import com.nhn.minidooray.taskapi.domain.response.ResultResponse;
import com.nhn.minidooray.taskapi.exception.ValidationFailedException;
import com.nhn.minidooray.taskapi.service.TaskTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.taskapi.requestmapping.prefix}")
public class TaskTagController {
    private final TaskTagService taskTagService;

    @PostMapping("${com.nhn.minidooray.taskapi.requestmapping.create-tasktag}")
    public ResultResponse<Void> createTaskTag(
            @PathVariable("projectId") Long projectId,
            @RequestBody @Valid TaskTagCreateRequest taskTagCreateRequest,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        taskTagService.createTaskTag(taskTagCreateRequest);
        return ResultResponse.created(null);
    }

    @DeleteMapping("${com.nhn.minidooray.taskapi.requestmapping.delete-tasktag}")
    public ResultResponse<Void> deleteTaskTag(
            @PathVariable("projectId") Long projectId,
            @PathVariable("tagId") Long tagId) {
        taskTagService.deleteTaskTag(projectId, tagId);
        return ResultResponse.deleted(null);
    }
}
