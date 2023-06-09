package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.ProjectAccountCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.ProjectAccountUpdateRequest;
import com.nhn.minidooray.taskapi.domain.request.ProjectCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.ProjectUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.AccountByProjectResponse;
import com.nhn.minidooray.taskapi.domain.response.ProjectByAccountResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {
    Long createProject(ProjectCreateRequest projectCreateRequest);

    Long updateProject(Long projectId, ProjectUpdateRequest projectUpdateRequest);

    void deleteProject(Long projectId);


    Page<ProjectByAccountResponse> getProjectsByAccount(String accountId, Pageable pageable);
}
