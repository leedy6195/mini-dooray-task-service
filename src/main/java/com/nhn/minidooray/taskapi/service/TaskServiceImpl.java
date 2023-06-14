package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.TaskCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.TaskUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.TasksResponse;
import com.nhn.minidooray.taskapi.entity.MilestoneEntity;
import com.nhn.minidooray.taskapi.entity.ProjectAccountEntity;
import com.nhn.minidooray.taskapi.entity.ProjectEntity;
import com.nhn.minidooray.taskapi.entity.TaskEntity;
import com.nhn.minidooray.taskapi.exception.NotFoundException;
import com.nhn.minidooray.taskapi.repository.MilestoneRepository;
import com.nhn.minidooray.taskapi.repository.ProjectAccountRepository;
import com.nhn.minidooray.taskapi.repository.ProjectRepository;
import com.nhn.minidooray.taskapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final ProjectAccountRepository projectAccountRepository;
    private final MilestoneRepository milestoneRepository;

    @Override
    @Transactional
    public Long createTask(TaskCreateRequest taskCreateRequest) {
        MilestoneEntity milestoneEntity = null;
        ProjectEntity projectEntity = projectRepository.findById(taskCreateRequest.getProjectId())
                .orElseThrow(() -> new NotFoundException("project"));
        if (taskCreateRequest.getMilestoneId() != null) {
            milestoneEntity = milestoneRepository.findById(taskCreateRequest.getMilestoneId())
                    .orElseThrow(() -> new NotFoundException("milestone"));
        }

        //프로젝트에 속한 회원인지 검사
        projectAccountRepository.findByPk(ProjectAccountEntity.Pk.builder()
                        .projectId(taskCreateRequest.getProjectId())
                        .accountId(taskCreateRequest.getWriterId()).build())
                .orElseThrow(() -> new NotFoundException("projectAccount"));

        return taskRepository.save(TaskEntity.builder().title(taskCreateRequest.getName())
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

        //프로젝트에 속한 회원으로의 변경인지 검사
        projectAccountRepository.findByPk(ProjectAccountEntity.Pk.builder()
                        .projectId(taskUpdateRequest.getProjectId())
                        .accountId(taskUpdateRequest.getWriterId()).build())
                .orElseThrow(() -> new NotFoundException("projectAccount"));

        taskEntity.update(taskUpdateRequest.getName(), projectEntity, taskUpdateRequest.getWriterId(), milestoneEntity);
        return taskId;
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("task"));
        taskRepository.delete(taskEntity);
    }

    @Override
    public Page<TasksResponse> getTasks(Long projectId, Pageable pageable) {
        return taskRepository.findAllByProjectEntity_Id(projectId, pageable);
    }
}
