package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.domain.response.TasksResponse;
import com.nhn.minidooray.taskapi.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    Page<TasksResponse> findAllByProjectEntity_Id(Long projectId, Pageable pageable);
}
