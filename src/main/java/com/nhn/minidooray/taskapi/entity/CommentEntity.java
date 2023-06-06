package com.nhn.minidooray.taskapi.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "comment")
@Entity
@Getter
@NoArgsConstructor
public class CommentEntity {
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
}
