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
import com.nhn.minidooray.taskapi.exception.AuthorityNotFoundException;
import com.nhn.minidooray.taskapi.exception.ProjectNotFoundException;
import com.nhn.minidooray.taskapi.exception.ProjectStateNotFoundException;
import com.nhn.minidooray.taskapi.repository.AuthorityRepository;
import com.nhn.minidooray.taskapi.repository.ProjectAccountRepository;
import com.nhn.minidooray.taskapi.repository.ProjectRepository;
import com.nhn.minidooray.taskapi.repository.ProjectStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service("projectService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectAccountRepository projectAccountRepository;
    private final AuthorityRepository authorityRepository;
    private final ProjectStateRepository projectStateRepository;


    private Optional<ProjectEntity> getProjectEntity(Long projectId) {
        return projectRepository.findById(projectId);
    }

    @Override
    @Transactional
    public Long createProject(ProjectCreateRequest projectCreateRequest) {
        AuthorityEntity authority = authorityRepository.findByAuthorityType(AuthorityType.관리자)
                .orElseThrow(() -> new AuthorityNotFoundException("Authority not found"));
        ProjectStateEntity projectState = projectStateRepository.findByProjectStateType(ProjectStateType.활성)
                .orElseThrow(() -> new ProjectStateNotFoundException("Project state not found"));

        ProjectEntity projectEntity = ProjectEntity.builder()
                .projectStateEntity(projectState)
                .name(projectCreateRequest.getProjectName())
                .createAt(LocalDateTime.now())
                .build();

        projectRepository.save(projectEntity);

        ProjectAccountEntity projectAccountEntity = ProjectAccountEntity.builder()
                .projectEntity(projectEntity)
                .authorityEntity(authority)
                .accountId(projectCreateRequest.getAccountId())
                .createAt(LocalDateTime.now())
                .pk(ProjectAccountEntity.Pk.builder()
                        .projectId(projectEntity.getId())
                        .authorityCode(authority.getAuthorityType().name())
                        .build())
                .build();

        projectAccountRepository.save(projectAccountEntity);

        return projectEntity.getId();
    }

    @Override
    @Transactional
    public Long updateProject(Long projectId, ProjectUpdateRequest projectUpdateRequest) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        ProjectStateEntity projectStateEntity = projectStateRepository.findById(projectUpdateRequest.getProjectStateCode())
                .orElseThrow(() -> new ProjectStateNotFoundException("Project state not found"));

        projectEntity.setName(projectUpdateRequest.getProjectName());
        projectEntity.setProjectStateEntity(projectStateEntity);

        projectRepository.save(projectEntity);

        return projectId;
    }

    @Override
    @Transactional
    public void deleteProject(Long projectId) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        projectAccountRepository.deleteById(
                ProjectAccountEntity.Pk.builder()
                        .projectId(projectId)
                        .build());

        projectRepository.delete(projectEntity);
    }

    @Override
    public List<ProjectByAccountResponse> getProjectsByAccount(String accountId) {
        return null;
    }
}
