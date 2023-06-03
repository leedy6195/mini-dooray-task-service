package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.ProjectCreateRequest;
import com.nhn.minidooray.taskapi.entity.AuthorityEntity;
import com.nhn.minidooray.taskapi.entity.ProjectAccountEntity;
import com.nhn.minidooray.taskapi.entity.ProjectEntity;
import com.nhn.minidooray.taskapi.entity.ProjectStateEntity;
import com.nhn.minidooray.taskapi.enums.AuthorityType;
import com.nhn.minidooray.taskapi.enums.ProjectStateType;
import com.nhn.minidooray.taskapi.repository.AuthorityRepository;
import com.nhn.minidooray.taskapi.repository.ProjectAccountRepository;
import com.nhn.minidooray.taskapi.repository.ProjectRepository;
import com.nhn.minidooray.taskapi.repository.ProjectStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service("projectService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectAccountRepository projectAccountRepository;
    private final AuthorityRepository authorityRepository;
    private final ProjectStateRepository projectStateRepository;

    @Override
    @Transactional
    public Long createProject(ProjectCreateRequest projectCreateRequest) {
        AuthorityEntity authority = authorityRepository.findByAuthorityType(AuthorityType.관리자);
        ProjectStateEntity projectState = projectStateRepository.findByProjectStateType(ProjectStateType.활성);

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
                .pk(new ProjectAccountEntity.Pk(
                        projectEntity.getId(),
                        authority.getAuthorityType().name()))
                .build();

        projectAccountRepository.save(projectAccountEntity);

        return projectEntity.getId();
    }
}
