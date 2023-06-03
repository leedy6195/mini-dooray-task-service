package com.nhn.minidooray.taskapi.controller;

import com.nhn.minidooray.taskapi.domain.request.ProjectCreateRequest;
import com.nhn.minidooray.taskapi.domain.response.ProjectCreateResponse;
import com.nhn.minidooray.taskapi.domain.response.ResultResponse;
import com.nhn.minidooray.taskapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.taskapi.requestmapping.prefix}")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("${com.nhn.minidooray.taskapi.requestmapping.create-project}")
    public ResultResponse<ProjectCreateResponse> createProject(@RequestBody @Valid ProjectCreateRequest projectCreateRequest,
                                              BindingResult bindingResult) {
        Long projectId = projectService.createProject(projectCreateRequest);
        return ResultResponse.<ProjectCreateResponse>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.CREATED.value())
                        .resultMessage("success")
                        .build())
                .result(List.of(ProjectCreateResponse.builder()
                        .projectId(projectId)
                        .build()))
                .build();

        //return ResultResponse.<Void>builder()
        //        .build();
    }

}