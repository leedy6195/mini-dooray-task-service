package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.domain.response.CommentByTaskResponse;
import com.nhn.minidooray.taskapi.entity.CommentEntity;
import com.nhn.minidooray.taskapi.entity.QCommentEntity;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class CommentRepositoryImpl extends QuerydslRepositorySupport implements CommentCustomRepository {
    public CommentRepositoryImpl() {
        super(CommentEntity.class);
    }

    @Override
    public Page<CommentByTaskResponse> findCommentsByTaskId(Long taskId, Pageable pageable) {
        QCommentEntity commentEntity = QCommentEntity.commentEntity;
        QCommentEntity subCommentEntity = new QCommentEntity("subCommentEntity");

        QueryResults<Tuple> results = from(commentEntity)
                .leftJoin(commentEntity.children, subCommentEntity).fetchJoin()
                .where(commentEntity.taskEntity.id.eq(taskId))
                .select(commentEntity, subCommentEntity)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<CommentByTaskResponse> commentResponses = results.getResults().stream()
                .map(tuple -> {
                    CommentEntity comment = tuple.get(commentEntity);
                    CommentEntity subComment = tuple.get(subCommentEntity);

                    CommentByTaskResponse commentResponse = new CommentByTaskResponse();
                    commentResponse.setTaskId(comment.getTaskEntity().getId());
                    commentResponse.setCommentId(comment.getId());
                    commentResponse.setCommentWriter(comment.getWriterId());
                    commentResponse.setCommentContent(comment.getContent());
                    commentResponse.setCommentCreateAt(comment.getCreateAt().toLocalDate());
                    commentResponse.setParentCommentId(comment.getParentComment() != null ? comment.getParentComment().getId() : null);

                    if (subComment != null) {
                        CommentByTaskResponse subCommentResponse = new CommentByTaskResponse();
                        subCommentResponse.setCommentId(subComment.getId());
                        subCommentResponse.setCommentContent(subComment.getContent());
                        subCommentResponse.setParentCommentId(subComment.getParentComment() != null ? subComment.getParentComment().getId() : null);

                        commentResponse.getChildren().add(subCommentResponse);
                    }

                    return commentResponse;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(commentResponses, pageable, results.getTotal());

    }
}
