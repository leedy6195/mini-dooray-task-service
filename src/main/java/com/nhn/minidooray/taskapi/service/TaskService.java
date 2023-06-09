package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.TaskCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.TaskUpdateRequest;

public interface TaskService {
    Long createTask(TaskCreateRequest taskCreateRequest);

    Long updateTask(Long taskId, TaskUpdateRequest taskUpdateRequest);

    void deleteTask(Long taskId);
}
