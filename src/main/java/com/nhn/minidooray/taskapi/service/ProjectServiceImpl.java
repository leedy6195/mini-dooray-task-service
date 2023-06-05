package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.ProjectCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.ProjectUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.ProjectByAccountResponse;
import com.nhn.minidooray.taskapi.entity.AuthorityEntity;
import com.nhn.minidooray.taskapi.entity.ProjectAccountEntity;
import com.nhn.minidooray.taskapi.entity.ProjectEntity;
import com.nhn.minidooray.taskapi.entity.ProjectStateEntity;
import com.nhn.minidooray.taskapi.enumerate.AuthorityType;
import com.nhn.minidooray.taskapi.enumerate.ProjectStateType;
import com.nhn.minidooray.taskapi.exception.NotFoundException;
import com.nhn.minidooray.taskapi.repository.AuthorityRepository;
import com.nhn.minidooray.taskapi.repository.ProjectAccountRepository;
import com.nhn.minidooray.taskapi.repository.ProjectRepository;
import com.nhn.minidooray.taskapi.repository.ProjectStateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("projectService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectAccountRepository projectAccountRepository;
    private final AuthorityRepository authorityRepository;
    private final ProjectStateRepository projectStateRepository;

    @Override
    @Transactional
    public Long createProject(ProjectCreateRequest projectCreateRequest) {
        AuthorityEntity authority = authorityRepository.findByAuthorityType(AuthorityType.관리자)
                .orElseThrow(() -> new NotFoundException("authority"));
        ProjectStateEntity projectState = projectStateRepository.findByProjectStateType(ProjectStateType.활성)
                .orElseThrow(() -> new NotFoundException("projectState"));

        ProjectEntity projectEntity = ProjectEntity.builder()
                .projectStateEntity(projectState)
                .name(projectCreateRequest.getProjectName())
                .build();

        projectRepository.save(projectEntity);

        ProjectAccountEntity projectAccountEntity = ProjectAccountEntity.builder()
                .projectEntity(projectEntity)
                .authorityEntity(authority)
                .pk(ProjectAccountEntity.Pk.builder()
                        .projectId(projectEntity.getId())
                        .accountId(projectCreateRequest.getAccountId())
                        .build())
                .build();

        projectAccountRepository.save(projectAccountEntity);

        return projectEntity.getId();
    }

    @Override
    @Transactional
    public Long updateProject(Long projectId, ProjectUpdateRequest projectUpdateRequest) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("project"));

        ProjectStateEntity projectStateEntity = projectStateRepository.findById(projectUpdateRequest.getProjectStateCode())
                .orElseThrow(() -> new NotFoundException("projectState"));

        projectEntity.setName(projectUpdateRequest.getProjectName());
        projectEntity.setProjectStateEntity(projectStateEntity);

        return projectRepository.save(projectEntity).getId();
    }

    @Override
    @Transactional
    public void deleteProject(Long projectId) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("project"));

        projectRepository.delete(projectEntity);
    }



    @Override
    public List<ProjectByAccountResponse> getProjectsByAccount(String accountId) {
        return null;
    }
}
