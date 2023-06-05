package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.domain.response.AccountByProjectResponse;
import com.nhn.minidooray.taskapi.entity.ProjectAccountEntity;

import java.util.List;

public interface ProjectAccountCustomRepository {
    List<AccountByProjectResponse> findAccountsByProjectId(Long projectId);
}
