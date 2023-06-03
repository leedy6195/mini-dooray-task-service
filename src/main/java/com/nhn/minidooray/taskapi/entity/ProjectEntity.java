package com.nhn.minidooray.taskapi.entity;

import com.nhn.minidooray.taskapi.enums.ProjectStateType;
import lombok.*;
import org.springframework.boot.actuate.autoconfigure.metrics.export.newrelic.NewRelicPropertiesConfigAdapter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "project")
@NoArgsConstructor
@Getter
@Setter
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "state_code")
    private ProjectStateEntity projectStateEntity;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Builder
    public ProjectEntity(Long id, String name, ProjectStateEntity projectStateEntity, LocalDateTime createAt) {
        this.id = id;
        this.name = name;
        this.projectStateEntity = projectStateEntity;
        this.createAt = createAt;
    }
}
