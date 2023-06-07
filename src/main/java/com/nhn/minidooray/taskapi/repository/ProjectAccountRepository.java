package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.domain.response.ProjectAccountRequest;
import com.nhn.minidooray.taskapi.entity.ProjectAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProjectAccountRepository extends JpaRepository<ProjectAccountEntity, ProjectAccountEntity.Pk>, ProjectAccountCustomRepository {
    Optional<ProjectAccountRequest> findByPk(ProjectAccountEntity.Pk pk);
}
