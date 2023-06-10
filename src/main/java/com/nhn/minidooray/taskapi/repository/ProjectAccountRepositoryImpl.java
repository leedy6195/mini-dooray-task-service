package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.domain.response.AccountByProjectResponse;
import com.nhn.minidooray.taskapi.entity.ProjectAccountEntity;
import com.nhn.minidooray.taskapi.entity.QAuthorityEntity;
import com.nhn.minidooray.taskapi.entity.QProjectAccountEntity;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;


public class ProjectAccountRepositoryImpl extends QuerydslRepositorySupport implements ProjectAccountCustomRepository {
    public ProjectAccountRepositoryImpl() {
        super(ProjectAccountEntity.class);
    }
    @Override
    public Page<AccountByProjectResponse> findAccountsByProjectId(Long projectId, Pageable pageable) {
        QProjectAccountEntity projectAccountEntity = QProjectAccountEntity.projectAccountEntity;
        QAuthorityEntity authorityEntity = QAuthorityEntity.authorityEntity;

        QueryResults<AccountByProjectResponse> results = from(projectAccountEntity)
                .leftJoin(projectAccountEntity.authorityEntity, authorityEntity)
                .where(projectAccountEntity.pk.projectId.eq(projectId))
                .select(Projections.fields(
                        AccountByProjectResponse.class,
                        projectAccountEntity.pk.projectId.as("projectId"),
                        projectAccountEntity.projectEntity.name.as("projectName"),
                        projectAccountEntity.pk.accountId.as("accountId"),
                        projectAccountEntity.authorityEntity.code.as("authorityCode"),
                        projectAccountEntity.authorityEntity.authorityType.as("authority")
                ))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }
}
