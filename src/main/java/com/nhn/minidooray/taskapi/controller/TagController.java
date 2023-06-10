package com.nhn.minidooray.taskapi.controller;

import com.nhn.minidooray.taskapi.domain.request.TagCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.TagUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.CommonResponse;
import com.nhn.minidooray.taskapi.domain.response.ResultResponse;
import com.nhn.minidooray.taskapi.domain.response.TagByProjectResponse;
import com.nhn.minidooray.taskapi.exception.ValidationFailedException;
import com.nhn.minidooray.taskapi.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.taskapi.requestmapping.prefix}")
public class TagController {
    private final TagService tagService;

    @PostMapping("${com.nhn.minidooray.taskapi.requestmapping.create-tag}")
    public ResultResponse<CommonResponse> createTag(
            @PathVariable("projectId") Long projectId,
            @RequestBody @Valid TagCreateRequest tagCreateRequest,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        Long tagId = tagService.createTag(projectId, tagCreateRequest);
        return ResultResponse.created(List.of(CommonResponse.builder().id(tagId).build()));
    }

    @PutMapping("${com.nhn.minidooray.taskapi.requestmapping.update-tag}")
    public ResultResponse<CommonResponse> updateTag(
            @PathVariable("projectId") Long projectId,
            @PathVariable("tagId") Long tagId,
            @RequestBody @Valid TagUpdateRequest tagUpdateRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        tagService.updateTag(projectId, tagId, tagUpdateRequest);
        return ResultResponse.updated(List.of(CommonResponse.builder().id(tagId).build()));
    }

    @DeleteMapping("${com.nhn.minidooray.taskapi.requestmapping.delete-tag}")
    public ResultResponse<Void> deleteTag(
            @PathVariable("projectId") Long projectId,
            @PathVariable("tagId") Long tagId) {
        tagService.deleteTag(projectId, tagId);
        return ResultResponse.deleted(null);
    }

    @GetMapping("projects/{projectId}/tags")
    public ResultResponse<List<TagByProjectResponse>> getTagsByProjectId(
            @PathVariable("projectId") Long projectId, Pageable pageable) {
        return ResultResponse.fetched(List.of(tagService.findTagsByProjectId(projectId, pageable).getContent()));
    }
}
