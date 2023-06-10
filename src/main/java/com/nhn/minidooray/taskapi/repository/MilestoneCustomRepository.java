package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.domain.response.MilestoneByProjectResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MilestoneCustomRepository {
    Page<MilestoneByProjectResponse> findMilestoneByProject(Long projectId, Pageable pageable);
}
