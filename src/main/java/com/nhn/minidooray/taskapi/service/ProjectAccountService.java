package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.ProjectAccountCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.ProjectAccountUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.AccountByProjectResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectAccountService {
    void createProjectAccount(Long projectId, ProjectAccountCreateRequest projectAccountCreateRequest);

    void updateProjectAccount(Long projectId, String accountId, ProjectAccountUpdateRequest projectAccountUpdateRequest);

    void deleteProjectAccount(Long projectId, String accountId);

    Page<AccountByProjectResponse> getAccountsByProject(Long projectId, Pageable pageable);
}
