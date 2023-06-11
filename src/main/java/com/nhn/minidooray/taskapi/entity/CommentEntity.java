package com.nhn.minidooray.taskapi.entity;

import com.nhn.minidooray.taskapi.domain.request.CommentUpdateRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Table(name = "comment")
@Entity
@Getter
@NoArgsConstructor
public class CommentEntity implements Updatable<CommentUpdateRequest> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CommentEntity parentComment;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private TaskEntity taskEntity;

    private String writerId;

    private String content;

    private LocalDateTime createAt;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<CommentEntity> children;

    @PrePersist
    public void setCreateAt() {
        this.createAt = LocalDateTime.now();
    }

    @Builder
    public CommentEntity(CommentEntity parentComment, TaskEntity taskEntity, String writerId, String content) {
        this.parentComment = parentComment;
        this.taskEntity = taskEntity;
        this.writerId = writerId;
        this.content = content;
    }

    @Override
    public void update(CommentUpdateRequest commentUpdateRequest) {
        if (Objects.nonNull(commentUpdateRequest.getContent())) this.content = commentUpdateRequest.getContent();

    }
}
