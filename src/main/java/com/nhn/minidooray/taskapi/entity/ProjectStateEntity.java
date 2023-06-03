package com.nhn.minidooray.taskapi.entity;


import com.nhn.minidooray.taskapi.enums.ProjectStateType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "project_state")
@NoArgsConstructor
@Getter
@Setter
public class ProjectStateEntity {
    @Id
    private String code;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private ProjectStateType projectStateType;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Builder
    public ProjectStateEntity(String code, ProjectStateType projectStateType, LocalDateTime createAt) {
        this.code = code;
        this.projectStateType = projectStateType;
        this.createAt = createAt;
    }
}
