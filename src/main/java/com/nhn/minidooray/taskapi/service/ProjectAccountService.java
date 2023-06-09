package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.ProjectAccountCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.ProjectAccountUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.AccountByProjectResponse;
import com.nhn.minidooray.taskapi.domain.response.ProjectAccountResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectAccountService {
    void createProjectAccount(Long projectId, ProjectAccountCreateRequest projectAccountCreateRequest);

    void updateProjectAccount(Long projectId, String accountId, ProjectAccountUpdateRequest projectAccountUpdateRequest);

    void deleteProjectAccount(Long projectId, String accountId);

    ProjectAccountResponse getAccountByProject(Long projectId, String accountId);

    Page<AccountByProjectResponse> getAccountsByProject(Long projectId, Pageable pageable);
}
