package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.TaskCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.TaskUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.TasksResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    Long createTask(TaskCreateRequest taskCreateRequest);

    Long updateTask(Long taskId, TaskUpdateRequest taskUpdateRequest);

    void deleteTask(Long taskId);

    Page<TasksResponse> getTasks(Long projectId, Pageable pageable);
}
