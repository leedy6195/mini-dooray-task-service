package com.nhn.minidooray.taskapi.controller;

import com.nhn.minidooray.taskapi.domain.request.ProjectCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.ProjectUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.CommonResponse;
import com.nhn.minidooray.taskapi.domain.response.ProjectByAccountResponse;
import com.nhn.minidooray.taskapi.domain.response.ResultResponse;
import com.nhn.minidooray.taskapi.exception.ValidationFailedException;
import com.nhn.minidooray.taskapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.taskapi.requestmapping.prefix}")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping("${com.nhn.minidooray.taskapi.requestmapping.create-project}")
    public ResultResponse<CommonResponse> createProject(
            @RequestBody @Valid ProjectCreateRequest projectCreateRequest,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        Long projectId = projectService.createProject(projectCreateRequest);
        return ResultResponse.created(List.of(CommonResponse.builder().id(projectId).build()));
    }

    @PutMapping("${com.nhn.minidooray.taskapi.requestmapping.update-project}")
    public ResultResponse<CommonResponse> updateProject(
            @PathVariable("projectId") Long projectId,
            @RequestBody @Valid ProjectUpdateRequest projectUpdateRequest,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        projectService.updateProject(projectId, projectUpdateRequest);
        return ResultResponse.updated(List.of(CommonResponse.builder().id(projectId).build()));
    }

    @DeleteMapping("${com.nhn.minidooray.taskapi.requestmapping.delete-project}")
    public ResultResponse<Void> deleteProject(@PathVariable("projectId") Long projectId) {
        projectService.deleteProject(projectId);
        return ResultResponse.deleted(null);
    }

    @GetMapping("/{accountId}/projects")
    public ResultResponse<Page<ProjectByAccountResponse>> getProjects(@PathVariable("accountId") String accountId, Pageable pageable) {
        Page<ProjectByAccountResponse> projects = projectService.getProjectsByAccount(accountId, pageable);
        return ResultResponse.fetched(List.of(projects));
    }
}