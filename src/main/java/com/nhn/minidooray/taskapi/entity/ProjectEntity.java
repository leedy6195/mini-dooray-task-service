package com.nhn.minidooray.taskapi.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    // for cascading remove
    @OneToMany(mappedBy = "projectEntity", cascade = CascadeType.REMOVE)
    private List<ProjectAccountEntity> projectAccountEntities;

    @PrePersist
    public void setCreateAt() {
        this.createAt = LocalDateTime.now();
    }
}
