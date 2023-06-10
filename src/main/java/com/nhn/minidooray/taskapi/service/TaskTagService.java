package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.TaskTagCreateRequest;

public interface TaskTagService {
    void createTaskTag(Long taskId, Long tagId);

    void deleteTaskTag(Long taskId, Long tagId);
}
