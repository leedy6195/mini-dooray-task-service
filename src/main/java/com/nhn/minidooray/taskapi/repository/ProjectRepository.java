package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.domain.response.ProjectResponse;
import com.nhn.minidooray.taskapi.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long>, ProjectCustomRepository {
    Optional<ProjectResponse> findProjectResponseById(Long projectId);
}
