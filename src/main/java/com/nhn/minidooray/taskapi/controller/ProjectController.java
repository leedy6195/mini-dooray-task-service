package com.nhn.minidooray.taskapi.controller;

import com.nhn.minidooray.taskapi.domain.request.ProjectCreateRequest;
import com.nhn.minidooray.taskapi.domain.response.ResultResponse;
import com.nhn.minidooray.taskapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.accountapi.requestmapping.prefix}")
public class ProjectController {

    private ProjectService projectService;

    @PostMapping("${com.nhn.minidooray.accountapi.requestmapping.create-project}")
    public ResultResponse<Void> createProject(@RequestBody @Valid ProjectCreateRequest projectCreateRequest,
                                              BindingResult bindingResult) {


        return ResultResponse.<Void>builder()
                .build();
    }

}