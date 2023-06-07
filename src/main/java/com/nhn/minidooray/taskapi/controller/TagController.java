package com.nhn.minidooray.taskapi.controller;

import com.nhn.minidooray.taskapi.config.ApiMessageProperties;
import com.nhn.minidooray.taskapi.domain.request.TagCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.TagUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.CommonResponse;
import com.nhn.minidooray.taskapi.domain.response.ResultResponse;
import com.nhn.minidooray.taskapi.exception.ValidationFailedException;
import com.nhn.minidooray.taskapi.service.TagService;
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
public class TagController implements MessageSourceAware {
    private final TagService tagService;

    private MessageSourceAccessor messageSourceAccessor;

    @PostMapping("${com.nhn.minidooray.taskapi.requestmapping.create-tag}")
    public ResultResponse<CommonResponse> createTag(
            @PathVariable("projectId") Long projectId,
            @RequestBody @Valid TagCreateRequest tagCreateRequest,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        Long tagId = tagService.createTag(projectId, tagCreateRequest);
        return ResultResponse.<CommonResponse>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.CREATED.value())
                        .resultMessage(messageSourceAccessor.getMessage("api.response.create.success"))
                        .build())
                .result(List.of(CommonResponse.builder().id(tagId).build()))
                .build();
    }

    @PutMapping("${com.nhn.minidooray.taskapi.requestmapping.update-tag}")
    public ResultResponse<Void> updateTag(
            @PathVariable("tagId") Long tagId,
            @RequestBody @Valid TagUpdateRequest tagUpdateRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        tagService.updateTag(tagId, tagUpdateRequest);
        return ResultResponse.<Void>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.OK.value())
                        .resultMessage(messageSourceAccessor.getMessage("api.response.update.success"))
                        .build())
                .build();
    }

    @DeleteMapping("${com.nhn.minidooray.taskapi.requestmapping.delete-tag}")
    public ResultResponse<Void> deleteTag(
            @PathVariable("tagId") Long tagId) {
        tagService.deleteTag(tagId);
        return ResultResponse.<Void>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.OK.value())
                        .resultMessage(messageSourceAccessor.getMessage("api.response.delete.success"))
                        .build())
                .build();
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }

}
