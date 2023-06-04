package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.entity.ProjectStateEntity;
import com.nhn.minidooray.taskapi.enumerate.ProjectStateType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectStateRepository extends JpaRepository<ProjectStateEntity, String> {
    Optional<ProjectStateEntity> findByProjectStateType(ProjectStateType projectStateType);
    Optional<ProjectStateEntity> findById(String projectStateCode);
}
