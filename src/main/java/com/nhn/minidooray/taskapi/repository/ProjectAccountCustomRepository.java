package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.domain.response.AccountByProjectResponse;
import com.nhn.minidooray.taskapi.entity.ProjectAccountEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;


public interface ProjectAccountCustomRepository {
    List<AccountByProjectResponse> findAccountsByProjectId(Long projectId);
}
