package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.ProjectCreateRequest;

public interface ProjectService {
    Long createProject(ProjectCreateRequest projectCreateRequest);
}
