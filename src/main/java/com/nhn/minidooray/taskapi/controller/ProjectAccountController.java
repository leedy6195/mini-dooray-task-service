package com.nhn.minidooray.taskapi.controller;

import com.nhn.minidooray.taskapi.config.ApiMessageProperties;
import com.nhn.minidooray.taskapi.config.RequestMappingProperties;
import com.nhn.minidooray.taskapi.domain.request.ProjectAccountCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.ProjectAccountUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.AccountByProjectResponse;
import com.nhn.minidooray.taskapi.domain.response.ResultResponse;
import com.nhn.minidooray.taskapi.exception.ValidationFailedException;
import com.nhn.minidooray.taskapi.service.ProjectAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.taskapi.requestmapping.prefix}")
public class ProjectAccountController {
    private final ProjectAccountService projectAccountService;
    private final ApiMessageProperties apiMessageProperties;

    @PostMapping("${com.nhn.minidooray.taskapi.requestmapping.create-account}")
    public ResultResponse<Void> createAccount(@PathVariable("projectId") Long projectId,
                                              @RequestBody @Valid ProjectAccountCreateRequest projectAccountCreateRequest,
                                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        projectAccountService.createProjectAccount(projectId, projectAccountCreateRequest);
        return ResultResponse.<Void>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.CREATED.value())
                        .resultMessage(apiMessageProperties.getCreateSuccMessage())
                        .build())
                .result(List.of())
                .build();
    }

    @PutMapping("${com.nhn.minidooray.taskapi.requestmapping.update-account}")
    public ResultResponse<Void> updateAccount(@PathVariable("projectId") Long projectId,
                                              @PathVariable("accountId") String accountId,
                                              @RequestBody @Valid ProjectAccountUpdateRequest projectAccountUpdateRequest,
                                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        projectAccountService.updateProjectAccount(projectId, accountId, projectAccountUpdateRequest);
        return ResultResponse.<Void>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.OK.value())
                        .resultMessage(apiMessageProperties.getUpdateSuccMessage())
                        .build())
                .result(List.of())
                .build();
    }

    @DeleteMapping("${com.nhn.minidooray.taskapi.requestmapping.delete-account}")
    public ResultResponse<Void> deleteAccount(@PathVariable("projectId") Long projectId,
                                              @PathVariable("accountId") String accountId) {
        projectAccountService.deleteProjectAccount(projectId, accountId);

        return ResultResponse.<Void>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.NO_CONTENT.value())
                        .resultMessage(apiMessageProperties.getDeleteSuccMessage())
                        .build())
                .result(List.of())
                .build();
    }

    @GetMapping("${com.nhn.minidooray.taskapi.requestmapping.get-accounts}")
    public ResultResponse<AccountByProjectResponse> getAccounts(@PathVariable("projectId") Long projectId) {
        List<AccountByProjectResponse> accounts = projectAccountService.getAccountsByProject(projectId);
        return ResultResponse.<AccountByProjectResponse>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.OK.value())
                        .resultMessage(apiMessageProperties.getGetSuccMessage())
                        .build())
                .result(accounts)
                .build();
    }
}
