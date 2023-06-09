package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.entity.TaskEntity;
import com.nhn.minidooray.taskapi.entity.TaskTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTagRepository extends JpaRepository<TaskTagEntity, TaskTagEntity.Pk> {
}
