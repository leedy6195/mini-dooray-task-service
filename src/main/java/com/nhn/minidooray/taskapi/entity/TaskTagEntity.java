package com.nhn.minidooray.taskapi.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "task_tag")
@Entity
@Getter
@NoArgsConstructor
public class TaskTagEntity {
    @EmbeddedId
    private Pk pk;

    @MapsId("taskId")
    @ManyToOne
    @JoinColumn(name = "task_id")
    private TaskEntity taskEntity;

    @MapsId("tagId")
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private TagEntity tagEntity;

    private LocalDateTime createAt;

    @PrePersist
    public void setCreateAt() {
        this.createAt = LocalDateTime.now();
    }

    @Builder
    public TaskTagEntity(Pk pk, TaskEntity taskEntity, TagEntity tagEntity) {
        this.pk = pk;
        this.taskEntity = taskEntity;
        this.tagEntity = tagEntity;
    }

    @NoArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Embeddable
    public static class Pk implements Serializable {
        private Long taskId;
        private Long tagId;

        @Builder
        public Pk(Long taskId, Long tagId) {
            this.taskId = taskId;
            this.tagId = tagId;
        }
    }
}
