package com.nhn.minidooray.taskapi.controller;

import com.nhn.minidooray.taskapi.domain.request.MilestoneCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.MilestoneUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.CommonResponse;
import com.nhn.minidooray.taskapi.domain.response.ResultResponse;
import com.nhn.minidooray.taskapi.exception.ValidationFailedException;
import com.nhn.minidooray.taskapi.service.MilestoneService;
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
public class MilestoneController implements MessageSourceAware {
    private final MilestoneService milestoneService;
    private MessageSourceAccessor messageSourceAccessor;

    @PostMapping("${com.nhn.minidooray.taskapi.requestmapping.create-milestone}")
    public ResultResponse<CommonResponse> createMilestone(
            @PathVariable("projectId") Long projectId,
            @RequestBody @Valid MilestoneCreateRequest milestoneCreateRequest,
                                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        Long milestoneId = milestoneService.createMilestone(projectId, milestoneCreateRequest);
        return ResultResponse.<CommonResponse>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.CREATED.value())
                        .resultMessage(messageSourceAccessor.getMessage("api.response.create.success"))
                        .build())
                .result(List.of(CommonResponse.builder().id(milestoneId).build()))
                .build();
    }

    @PutMapping("${com.nhn.minidooray.taskapi.requestmapping.update-milestone}")
    public ResultResponse<Void> updateMilestone(
            @PathVariable("milestoneId") Long milestoneId,
            @RequestBody @Valid MilestoneUpdateRequest milestoneUpdateRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        milestoneService.updateMilestone(milestoneId, milestoneUpdateRequest);
        return ResultResponse.<Void>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.OK.value())
                        .resultMessage(messageSourceAccessor.getMessage("api.response.update.success"))
                        .build())
                .result(List.of())
                .build();
    }

    @DeleteMapping("${com.nhn.minidooray.taskapi.requestmapping.delete-milestone}")
    public ResultResponse<Void> deleteMilestone(
            @PathVariable("milestoneId") Long milestoneId) {
        milestoneService.deleteMilestone(milestoneId);
        return ResultResponse.<Void>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.NO_CONTENT.value())
                        .resultMessage(messageSourceAccessor.getMessage("api.response.delete.success"))
                        .build())
                .result(List.of())
                .build();
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }
}