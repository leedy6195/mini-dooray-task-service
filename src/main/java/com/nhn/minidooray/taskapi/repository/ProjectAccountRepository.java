package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.entity.ProjectAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ProjectAccountRepository extends JpaRepository<ProjectAccountEntity, ProjectAccountEntity.Pk>, ProjectAccountCustomRepository {

}
