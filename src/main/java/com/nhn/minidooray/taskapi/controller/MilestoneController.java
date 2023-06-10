package com.nhn.minidooray.taskapi.controller;

import com.nhn.minidooray.taskapi.domain.request.MilestoneCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.MilestoneUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.CommonResponse;
import com.nhn.minidooray.taskapi.domain.response.MilestoneByProjectResponse;
import com.nhn.minidooray.taskapi.domain.response.ResultResponse;
import com.nhn.minidooray.taskapi.exception.ValidationFailedException;
import com.nhn.minidooray.taskapi.service.MilestoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.taskapi.requestmapping.prefix}")
public class MilestoneController {
    private final MilestoneService milestoneService;

    @PostMapping("${com.nhn.minidooray.taskapi.requestmapping.create-milestone}")
    public ResultResponse<CommonResponse> createMilestone(
            @PathVariable("projectId") Long projectId,
            @RequestBody @Valid MilestoneCreateRequest milestoneCreateRequest,
                                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        Long milestoneId = milestoneService.createMilestone(projectId, milestoneCreateRequest);
        return ResultResponse.updated(
                List.of(CommonResponse.builder().id(milestoneId).build()));
    }

    @PutMapping("${com.nhn.minidooray.taskapi.requestmapping.update-milestone}")
    public ResultResponse<CommonResponse> updateMilestone(
            @PathVariable("milestoneId") Long milestoneId,
            @RequestBody @Valid MilestoneUpdateRequest milestoneUpdateRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        milestoneService.updateMilestone(milestoneId, milestoneUpdateRequest);
        return ResultResponse.updated(
                List.of(CommonResponse.builder().id(milestoneId).build()));
    }

    @DeleteMapping("${com.nhn.minidooray.taskapi.requestmapping.delete-milestone}")
    public ResultResponse<Void> deleteMilestone(
            @PathVariable("milestoneId") Long milestoneId) {
        milestoneService.deleteMilestone(milestoneId);
        return ResultResponse.deleted(null);
    }

    @GetMapping("/projects/{projectId}/milestones")
    public ResultResponse<Page<MilestoneByProjectResponse>> getMilestonesByProject(
            @PathVariable("projectId") Long projectId, Pageable pageable) {
        Page<MilestoneByProjectResponse> milestoneByProjectResponses = milestoneService.findMilestonesByProject(projectId, pageable);
        return ResultResponse.fetched(List.of(milestoneByProjectResponses));
    }
}
