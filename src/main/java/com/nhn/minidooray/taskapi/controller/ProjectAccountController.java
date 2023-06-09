package com.nhn.minidooray.taskapi.controller;

import com.nhn.minidooray.taskapi.domain.request.ProjectAccountCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.ProjectAccountUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.AccountByProjectResponse;
import com.nhn.minidooray.taskapi.domain.response.ProjectAccountResponse;
import com.nhn.minidooray.taskapi.domain.response.ResultResponse;
import com.nhn.minidooray.taskapi.exception.ValidationFailedException;
import com.nhn.minidooray.taskapi.service.ProjectAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.taskapi.requestmapping.prefix}")
public class ProjectAccountController implements MessageSourceAware {
    private final ProjectAccountService projectAccountService;

    private MessageSourceAccessor messageSourceAccessor;


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
                        .resultMessage(messageSourceAccessor.getMessage("api.response.create.success"))
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
                        .resultMessage(messageSourceAccessor.getMessage("api.response.update.success"))
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
                        .resultMessage(messageSourceAccessor.getMessage("api.response.delete.success"))
                        .build())
                .result(List.of())
                .build();
    }

    @GetMapping("${com.nhn.minidooray.taskapi.requestmapping.get-accounts}")
    public ResultResponse<Page<AccountByProjectResponse>> getAccounts(@PathVariable("projectId") Long projectId, Pageable pageable) {
        Page<AccountByProjectResponse> accounts = projectAccountService.getAccountsByProject(projectId, pageable);
        return ResultResponse.<Page<AccountByProjectResponse>>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.OK.value())
                        .resultMessage(messageSourceAccessor.getMessage("api.response.get.success"))
                        .build())
                .result(List.of(accounts))
                .build();
    }

    @GetMapping("${com.nhn.minidooray.taskapi.requestmapping.get-account}")
    public ResultResponse<ProjectAccountResponse> getAccount(@PathVariable("projectId") Long projectId,
                                                             @PathVariable("accountId") String accountId) {
        ProjectAccountResponse account = projectAccountService.getAccountByProject(projectId, accountId);
        return ResultResponse.<ProjectAccountResponse>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.OK.value())
                        .resultMessage(messageSourceAccessor.getMessage("api.response.get.success"))
                        .build())
                .result(List.of(account))
                .build();
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }
}
