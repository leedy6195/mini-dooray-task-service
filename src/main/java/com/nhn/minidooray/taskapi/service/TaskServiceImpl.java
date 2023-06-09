package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.TaskCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.TaskUpdateRequest;
import com.nhn.minidooray.taskapi.entity.MilestoneEntity;
import com.nhn.minidooray.taskapi.entity.ProjectEntity;
import com.nhn.minidooray.taskapi.entity.TaskEntity;
import com.nhn.minidooray.taskapi.exception.NotFoundException;
import com.nhn.minidooray.taskapi.repository.MilestoneRepository;
import com.nhn.minidooray.taskapi.repository.ProjectRepository;
import com.nhn.minidooray.taskapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final MilestoneRepository milestoneRepository;
    @Override
    @Transactional
    public Long createTask(TaskCreateRequest taskCreateRequest) {
        ProjectEntity projectEntity = projectRepository.findById(taskCreateRequest.getProjectId())
                .orElseThrow(() -> new NotFoundException("project"));
        MilestoneEntity milestoneEntity = milestoneRepository.findById(taskCreateRequest.getMilestoneId())
                .orElseThrow(() -> new NotFoundException("milestone"));

        return taskRepository.save(TaskEntity.builder().name(taskCreateRequest.getName())
                        .writerId(taskCreateRequest.getWriterId())
                        .projectEntity(projectEntity)
                        .milestoneEntity(milestoneEntity)
                        .build()).getId();
    }

    @Override
    @Transactional
    public Long updateTask(Long taskId, TaskUpdateRequest taskUpdateRequest) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("task"));
        ProjectEntity projectEntity = projectRepository.findById(taskUpdateRequest.getProjectId())
                .orElseThrow(() -> new NotFoundException("project"));
        MilestoneEntity milestoneEntity = milestoneRepository.findById(taskUpdateRequest.getMilestoneId())
                .orElseThrow(() -> new NotFoundException("milestone"));

        taskEntity.update(taskUpdateRequest.getName(), projectEntity, taskUpdateRequest.getWriterId(), milestoneEntity);
        return taskRepository.save(taskEntity).getId();
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("task"));
        taskRepository.delete(taskEntity);
    }
}
