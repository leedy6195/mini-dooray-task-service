package com.nhn.minidooray.taskapi.controller;

import com.nhn.minidooray.taskapi.domain.request.ProjectCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.ProjectUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.CommonResponse;
import com.nhn.minidooray.taskapi.domain.response.ProjectByAccountResponse;
import com.nhn.minidooray.taskapi.domain.response.ResultResponse;
import com.nhn.minidooray.taskapi.exception.ValidationFailedException;
import com.nhn.minidooray.taskapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.taskapi.requestmapping.prefix}")
public class ProjectController implements MessageSourceAware {

    private final ProjectService projectService;

    private MessageSourceAccessor messageSourceAccessor;

    @PostMapping("${com.nhn.minidooray.taskapi.requestmapping.create-project}")
    public ResultResponse<CommonResponse> createProject(@RequestBody @Valid ProjectCreateRequest projectCreateRequest,
                                                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        Long projectId = projectService.createProject(projectCreateRequest);
        return ResultResponse.<CommonResponse>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.CREATED.value())
                        .resultMessage(messageSourceAccessor.getMessage("api.response.create.success"))
                        .build())
                .result(List.of(CommonResponse.builder().id(projectId).build()))
                .build();
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
        return ResultResponse.<CommonResponse>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.OK.value())
                        .resultMessage(messageSourceAccessor.getMessage("api.response.update.success"))
                        .build())
                .result(List.of(CommonResponse.builder()
                        .id(projectId)
                        .build()))
                .build();
    }

    @DeleteMapping("${com.nhn.minidooray.taskapi.requestmapping.delete-project}")
    public ResultResponse<CommonResponse> deleteProject(@PathVariable("projectId") Long projectId) {
        projectService.deleteProject(projectId);
        return ResultResponse.<CommonResponse>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.NO_CONTENT.value())
                        .resultMessage(messageSourceAccessor.getMessage("api.response.delete.success"))
                        .build())
                .result(List.of(CommonResponse.builder()
                        .id(projectId)
                        .build()))
                .build();
    }

    @GetMapping("/{accountId}/projects")
    public ResultResponse<ProjectByAccountResponse> getProjects(@PathVariable("accountId") String accountId) {
        List<ProjectByAccountResponse> projects = projectService.getProjectsByAccount(accountId);
        return ResultResponse.<ProjectByAccountResponse>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.OK.value())
                        .resultMessage(messageSourceAccessor.getMessage("api.response.get.success"))
                        .build())
                .result(projects)
                .build();
    }


    @Override
    public void setMessageSource(MessageSource messageSource) {
        messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }
}