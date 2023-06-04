package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.ProjectCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.ProjectUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.ProjectByAccountResponse;

import java.util.List;

public interface ProjectService {
    Long createProject(ProjectCreateRequest projectCreateRequest);

    Long updateProject(Long projectId, ProjectUpdateRequest projectUpdateRequest);

    void deleteProject(Long projectId);

    List<ProjectByAccountResponse> getProjectsByAccount(String accountId);
}
