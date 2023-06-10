package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.MilestoneCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.MilestoneUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.MilestoneByProjectResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MilestoneService {
    Long createMilestone(Long projectId, MilestoneCreateRequest milestoneCreateRequest);

    Long updateMilestone(Long milestoneId, MilestoneUpdateRequest milestoneUpdateRequest);

    void deleteMilestone(Long milestoneId);

    Page<MilestoneByProjectResponse> findMilestonesByProject(Long projectId, Pageable pageable);
}
