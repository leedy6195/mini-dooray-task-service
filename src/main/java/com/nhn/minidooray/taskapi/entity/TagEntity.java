package com.nhn.minidooray.taskapi.entity;

import com.nhn.minidooray.taskapi.domain.request.TagUpdateRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "tag")
@Entity
@Getter
@NoArgsConstructor
public class TagEntity implements Updatable<TagUpdateRequest> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity projectEntity;

    private String name;

    private LocalDateTime createAt;

    @PrePersist
    public void setCreateAt() { this.createAt = LocalDateTime.now(); }

    @Builder
    public TagEntity(ProjectEntity projectEntity, String name) {
        this.projectEntity = projectEntity;
        this.name = name;
    }

    @Override
    public void update(TagUpdateRequest tagUpdateRequest) {
        this.name = tagUpdateRequest.getName();
    }
}
