package com.nhn.minidooray.taskapi.controller;

import com.nhn.minidooray.taskapi.service.TaskTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskTagController {
    private final TaskTagService taskTagService;


}
