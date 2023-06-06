package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.MilestoneCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.MilestoneUpdateRequest;

public interface MilestoneService {
    Long createMilestone(Long projectId, MilestoneCreateRequest milestoneCreateRequest);

    Long updateMilestone(Long milestoneId, MilestoneUpdateRequest milestoneUpdateRequest);

    void deleteMilestone(Long milestoneId);
}
