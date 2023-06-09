package com.nhn.minidooray.taskapi.entity;

import com.nhn.minidooray.taskapi.domain.request.TaskUpdateRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "task")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity projectEntity;

    private String writerId;

    @ManyToOne
    @JoinColumn(name = "mile_stone_id")
    private MilestoneEntity milestoneEntity;

    private LocalDateTime createAt;

    @OneToMany(mappedBy = "taskEntity", cascade = CascadeType.REMOVE)
    private List<TaskTagEntity> taskTagEntities;

    @PrePersist
    public void setCreateAt() {
        this.createAt = LocalDateTime.now();
    }

    @Builder
    public TaskEntity(String name, ProjectEntity projectEntity, String writerId, MilestoneEntity milestoneEntity) {
        this.name = name;
        this.projectEntity = projectEntity;
        this.writerId = writerId;
        this.milestoneEntity = milestoneEntity;
    }

    public void update(String name, ProjectEntity projectEntity, String writerId, MilestoneEntity milestoneEntity) {
        this.name = name == null ? this.name : name;
        this.projectEntity = projectEntity == null ? this.projectEntity : projectEntity;
        this.writerId = writerId == null ? this.writerId : writerId;
        this.milestoneEntity = milestoneEntity == null ? this.milestoneEntity : milestoneEntity;
    }
}
