package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.entity.ProjectAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectAccountRepository extends JpaRepository<ProjectAccountEntity, ProjectAccountEntity.Pk> {
}
