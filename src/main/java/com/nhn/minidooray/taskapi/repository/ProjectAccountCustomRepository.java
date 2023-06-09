package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.domain.response.AccountByProjectResponse;
import com.nhn.minidooray.taskapi.entity.ProjectAccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;


public interface ProjectAccountCustomRepository {
    Page<AccountByProjectResponse> findAccountsByProjectId(Long projectId, Pageable pageable);
}
