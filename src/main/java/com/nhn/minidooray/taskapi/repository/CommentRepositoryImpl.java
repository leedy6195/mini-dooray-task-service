package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.domain.response.CommentByTaskResponse;
import com.nhn.minidooray.taskapi.entity.CommentEntity;
import com.nhn.minidooray.taskapi.entity.QCommentEntity;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;


public class CommentRepositoryImpl extends QuerydslRepositorySupport implements CommentCustomRepository {
    public CommentRepositoryImpl() {
        super(CommentEntity.class);
    }

    @Override
    public Page<CommentByTaskResponse> findCommentsByTaskId(Long taskId, Pageable pageable) {
        QCommentEntity commentEntity = QCommentEntity.commentEntity;
        QueryResults<CommentByTaskResponse> results = from(commentEntity)
                .where(commentEntity.taskEntity.id.eq(taskId))
                .select(Projections.fields(
                        CommentByTaskResponse.class,
                        commentEntity.taskEntity.id.as("taskId"),
                        commentEntity.id.as("commentId"),
                        commentEntity.writerId.as("commentWriter"),
                        commentEntity.content.as("commentContent"),
                        commentEntity.createAt.as("commentCreateAt"),
                        commentEntity.parentComment.id.as("parentCommentId")
                ))
                .orderBy(commentEntity.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }
}
