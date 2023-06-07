package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.ProjectAccountCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.ProjectAccountUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.AccountByProjectResponse;
import com.nhn.minidooray.taskapi.domain.response.ProjectAccountRequest;

import java.util.List;

public interface ProjectAccountService {
    void createProjectAccount(Long projectId, ProjectAccountCreateRequest projectAccountCreateRequest);

    void updateProjectAccount(Long projectId, String accountId, ProjectAccountUpdateRequest projectAccountUpdateRequest);

    void deleteProjectAccount(Long projectId, String accountId);

    List<AccountByProjectResponse> getAccountsByProject(Long projectId);

    ProjectAccountRequest getAccountByProject(Long projectId, String accountId);
}
