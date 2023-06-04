package com.nhn.minidooray.taskapi.entity;


import com.nhn.minidooray.taskapi.enumerate.ProjectStateType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "project_state")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProjectStateEntity {
    @Id
    private String code;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private ProjectStateType projectStateType;

    private LocalDateTime createAt;

    @PrePersist
    public void setCreateAt() {
        this.createAt = LocalDateTime.now();
    }
}
