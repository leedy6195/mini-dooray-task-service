package com.nhn.minidooray.taskapi.controller;

import com.nhn.minidooray.taskapi.domain.response.ResultResponse;
import com.nhn.minidooray.taskapi.service.TaskTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.taskapi.requestmapping.prefix}")
public class TaskTagController {
    private final TaskTagService taskTagService;

    @PostMapping("${com.nhn.minidooray.taskapi.requestmapping.create-tasktag}")
    public ResultResponse<Void> createTaskTag(
            @PathVariable("taskId") Long taskId,
            @PathVariable("tagId") Long tagId) {

        taskTagService.createTaskTag(taskId, tagId);
        return ResultResponse.created(null);
    }

    @DeleteMapping("${com.nhn.minidooray.taskapi.requestmapping.delete-tasktag}")
    public ResultResponse<Void> deleteTaskTag(
            @PathVariable("taskId") Long taskId,
            @PathVariable("tagId") Long tagId) {

        taskTagService.deleteTaskTag(taskId, tagId);
        return ResultResponse.deleted(null);
    }
}
