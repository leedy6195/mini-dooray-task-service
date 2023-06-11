package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.domain.response.TagByProjectResponse;
import com.nhn.minidooray.taskapi.domain.response.TagByTaskResponse;
import com.nhn.minidooray.taskapi.entity.QTagEntity;
import com.nhn.minidooray.taskapi.entity.QTaskTagEntity;
import com.nhn.minidooray.taskapi.entity.TagEntity;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class TagRepositoryImpl extends QuerydslRepositorySupport implements TagCustomRepository {
    public TagRepositoryImpl() {
        super(TagEntity.class);
    }

    @Override
    public Page<TagByProjectResponse> findTagsByProjectId(Long projectId, Pageable pageable) {
        QTagEntity tagEntity = QTagEntity.tagEntity;
        QueryResults<TagByProjectResponse> results = from(tagEntity)
                .where(tagEntity.projectEntity.id.eq(projectId))
                .select(Projections.fields(
                        TagByProjectResponse.class,
                        tagEntity.projectEntity.id.as("projectId"),
                        tagEntity.id.as("tagId"),
                        tagEntity.name.as("tagName")
                ))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public Page<TagByTaskResponse> findTagsByTaskId(Long taskId, Pageable pageable) {
        QTaskTagEntity taskTagEntity = QTaskTagEntity.taskTagEntity;
        QTagEntity tagEntity = QTagEntity.tagEntity;
        QueryResults<TagByTaskResponse> results = from(taskTagEntity)
                .leftJoin(taskTagEntity.tagEntity, tagEntity)
                .where(taskTagEntity.taskEntity.id.eq(taskId))
                .select(Projections.fields(
                        TagByTaskResponse.class,
                        taskTagEntity.taskEntity.id.as("taskId"),
                        tagEntity.id.as("tagId"),
                        tagEntity.name.as("tagName")
                ))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }
}
