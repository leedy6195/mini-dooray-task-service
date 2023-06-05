package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.ProjectAccountCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.ProjectAccountUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.AccountByProjectResponse;
import com.nhn.minidooray.taskapi.entity.AuthorityEntity;
import com.nhn.minidooray.taskapi.entity.ProjectAccountEntity;
import com.nhn.minidooray.taskapi.entity.ProjectEntity;
import com.nhn.minidooray.taskapi.exception.AlreadyExistsException;
import com.nhn.minidooray.taskapi.exception.NotFoundException;
import com.nhn.minidooray.taskapi.repository.AuthorityRepository;
import com.nhn.minidooray.taskapi.repository.ProjectAccountRepository;
import com.nhn.minidooray.taskapi.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("projectAccountService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectAccountServiceImpl implements ProjectAccountService {

    private final ProjectRepository projectRepository;
    private final ProjectAccountRepository projectAccountRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    @Transactional
    public void createProjectAccount(Long projectId, ProjectAccountCreateRequest projectAccountCreateRequest) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("project"));

        AuthorityEntity authorityEntity = authorityRepository.findById(projectAccountCreateRequest.getAuthorityCode())
                .orElseThrow(() -> new NotFoundException("authority"));

        projectAccountRepository.findById(
                        ProjectAccountEntity.Pk.builder()
                                .projectId(projectId)
                                .accountId(projectAccountCreateRequest.getAccountId())
                                .build())
                .ifPresent(projectAccountEntity -> {
                    throw new AlreadyExistsException("projectAccount");
                });

        projectAccountRepository.save(ProjectAccountEntity.builder()
                .pk(ProjectAccountEntity.Pk.builder()
                        .projectId(projectId)
                        .accountId(projectAccountCreateRequest.getAccountId())
                        .build())
                .projectEntity(projectEntity)
                .authorityEntity(authorityEntity)
                .build());
    }

    @Override
    @Transactional
    public void updateProjectAccount(Long projectId, String accountId, ProjectAccountUpdateRequest projectAccountUpdateRequest) {
        ProjectAccountEntity projectAccountEntity = projectAccountRepository.findById(
                        ProjectAccountEntity.Pk.builder()
                                .projectId(projectId)
                                .accountId(accountId)
                                .build())
                .orElseThrow(() -> new NotFoundException("project"));

        AuthorityEntity authorityEntity = authorityRepository.findById(projectAccountUpdateRequest.getAuthorityCode())
                .orElseThrow(() -> new NotFoundException("authority"));

        projectAccountEntity.setAuthorityEntity(authorityEntity);
        projectAccountRepository.save(projectAccountEntity);
    }

    @Override
    public void deleteProjectAccount(Long projectId, String accountId) {
        ProjectAccountEntity projectAccountEntity = projectAccountRepository.findById(
                        ProjectAccountEntity.Pk.builder()
                                .projectId(projectId)
                                .accountId(accountId)
                                .build())
                .orElseThrow(() -> new NotFoundException("projectAccount"));

        projectAccountRepository.delete(projectAccountEntity);
    }


    @Override
    public List<AccountByProjectResponse> getAccountsByProject(Long projectId) {
        return projectAccountRepository.findAccountsByProjectId(projectId);
    }
}
