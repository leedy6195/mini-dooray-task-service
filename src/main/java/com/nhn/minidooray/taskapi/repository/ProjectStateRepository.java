package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.entity.ProjectStateEntity;
import com.nhn.minidooray.taskapi.enums.ProjectStateType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectStateRepository extends JpaRepository<ProjectStateEntity, String> {
    ProjectStateEntity findByProjectStateType(ProjectStateType projectStateType);
}
