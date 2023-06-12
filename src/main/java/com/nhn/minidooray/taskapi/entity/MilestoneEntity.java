package com.nhn.minidooray.taskapi.entity;

import com.nhn.minidooray.taskapi.domain.request.MilestoneUpdateRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "mile_stone")
@Entity
@Getter
@NoArgsConstructor
public class MilestoneEntity implements Updatable<MilestoneUpdateRequest> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity projectEntity;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDateTime createAt;

    @PrePersist
    public void setCreateAt() {
        this.createAt = LocalDateTime.now();
    }

    @Builder
    public MilestoneEntity(Long id, ProjectEntity projectEntity, String name, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.projectEntity = projectEntity;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public void update(MilestoneUpdateRequest milestoneUpdateRequest) {
        this.name = milestoneUpdateRequest.getName();
        this.startDate = milestoneUpdateRequest.getStartDate();
        this.endDate = milestoneUpdateRequest.getEndDate();
    }
}
