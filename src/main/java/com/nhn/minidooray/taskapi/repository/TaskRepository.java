package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
