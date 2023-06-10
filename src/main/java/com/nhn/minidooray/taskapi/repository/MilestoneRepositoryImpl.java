package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.domain.response.MilestoneByProjectResponse;
import com.nhn.minidooray.taskapi.entity.MilestoneEntity;
import com.nhn.minidooray.taskapi.entity.QMilestoneEntity;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class MilestoneRepositoryImpl extends QuerydslRepositorySupport implements MilestoneCustomRepository {
    public MilestoneRepositoryImpl() {
        super(MilestoneEntity.class);
    }


    @Override
    public Page<MilestoneByProjectResponse> findMilestoneByProject(Long projectId, Pageable pageable) {
        QMilestoneEntity milestoneEntity = QMilestoneEntity.milestoneEntity;
        QueryResults<MilestoneByProjectResponse> results = from(milestoneEntity)
                .where(milestoneEntity.projectEntity.id.eq(projectId))
                .select(Projections.fields(
                        MilestoneByProjectResponse.class,
                        milestoneEntity.projectEntity.id.as("projectId"),
                        milestoneEntity.id.as("milestoneId"),
                        milestoneEntity.name.as("milestoneName"),
                        milestoneEntity.startDate.as("startDate"),
                        milestoneEntity.endDate.as("endDate")
                ))
                .orderBy(milestoneEntity.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }
}
