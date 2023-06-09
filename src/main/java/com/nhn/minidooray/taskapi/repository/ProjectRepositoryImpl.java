package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.domain.response.ProjectByAccountResponse;
import com.nhn.minidooray.taskapi.entity.ProjectAccountEntity;
import com.nhn.minidooray.taskapi.entity.QProjectAccountEntity;
import com.nhn.minidooray.taskapi.entity.QProjectEntity;
import com.nhn.minidooray.taskapi.entity.QProjectStateEntity;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class ProjectRepositoryImpl extends QuerydslRepositorySupport implements ProjectCustomRepository {
    public ProjectRepositoryImpl() {
        super(ProjectAccountEntity.class);
    }

    @Override
    public Page<ProjectByAccountResponse> findProjectsByAccountId(String accountId, Pageable pageable) {
        QProjectAccountEntity projectAccountEntity = QProjectAccountEntity.projectAccountEntity;
        QProjectEntity projectEntity = QProjectEntity.projectEntity;
        QProjectStateEntity projectStateEntity = QProjectStateEntity.projectStateEntity;

        QueryResults<ProjectByAccountResponse> results = from(projectAccountEntity)
                .leftJoin(projectAccountEntity.projectEntity, projectEntity)
                .leftJoin(projectEntity.projectStateEntity, projectStateEntity)
                .where(projectAccountEntity.pk.accountId.eq(accountId))
                .select(Projections.fields(
                        ProjectByAccountResponse.class,
                        projectAccountEntity.pk.accountId.as("accountId"),
                        projectAccountEntity.pk.projectId.as("projectId"),
                        projectAccountEntity.projectEntity.name.as("projectName"),
                        projectStateEntity.projectStateType.as("projectState")
                ))
                .orderBy(projectEntity.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }
}
