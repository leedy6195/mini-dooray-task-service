package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.ProjectCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.ProjectUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.ProjectByAccountResponse;
import com.nhn.minidooray.taskapi.domain.response.ProjectResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
    Long createProject(ProjectCreateRequest projectCreateRequest);

    Long updateProject(Long projectId, ProjectUpdateRequest projectUpdateRequest);

    void deleteProject(Long projectId);


    Page<ProjectByAccountResponse> getProjectsByAccount(String accountId, Pageable pageable);

    ProjectResponse getProject(Long projectId);
}
