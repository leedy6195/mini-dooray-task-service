package com.nhn.minidooray.taskapi.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "task")
@Entity
@Getter
@NoArgsConstructor
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity projectEntity;

    private String writerId;

    @ManyToOne
    @JoinColumn(name = "mile_stone_id")
    private MilestoneEntity milestoneEntity;

    private LocalDateTime createAt;

    @PrePersist
    public void setCreateAt() {
        this.createAt = LocalDateTime.now();
    }

    @Builder
    public TaskEntity(ProjectEntity projectEntity, String writerId, MilestoneEntity milestoneEntity) {
        this.projectEntity = projectEntity;
        this.writerId = writerId;
        this.milestoneEntity = milestoneEntity;
    }
}
