package com.nhn.minidooray.taskapi.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "project")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "state_code")
    private ProjectStateEntity projectStateEntity;

    private LocalDateTime createAt;

    @PrePersist
    public void setCreateAt() {
        this.createAt = LocalDateTime.now();
    }
}
