package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.MilestoneCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.MilestoneUpdateRequest;
import com.nhn.minidooray.taskapi.entity.MilestoneEntity;
import com.nhn.minidooray.taskapi.entity.ProjectEntity;
import com.nhn.minidooray.taskapi.exception.NotFoundException;
import com.nhn.minidooray.taskapi.repository.MilestoneRepository;
import com.nhn.minidooray.taskapi.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MilestoneServiceImpl implements MilestoneService {
    private final ProjectRepository projectRepository;
    private final MilestoneRepository milestoneRepository;

    @Override
    @Transactional
    public Long createMilestone(Long projectId, MilestoneCreateRequest milestoneCreateRequest) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("project"));

        return milestoneRepository.save(MilestoneEntity.builder()
                        .projectEntity(projectEntity)
                        .name(milestoneCreateRequest.getName())
                        .startDate(milestoneCreateRequest.getStartDate())
                        .endDate(milestoneCreateRequest.getEndDate())
                        .build())
                    .getId();
    }

    @Override
    @Transactional
    public Long updateMilestone(Long milestoneId, MilestoneUpdateRequest milestoneUpdateRequest) {
        MilestoneEntity milestoneEntity = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new NotFoundException("milestone"));

        milestoneEntity.update(milestoneUpdateRequest);
        return milestoneEntity.getId();
    }

    @Override
    @Transactional
    public void deleteMilestone(Long milestoneId) {
        MilestoneEntity milestoneEntity = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new NotFoundException("milestone"));

        milestoneRepository.delete(milestoneEntity);
    }
}
