package com.nhn.minidooray.taskapi.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "project_account")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProjectAccountEntity {
    @EmbeddedId
    private Pk pk;

    @MapsId("projectId")
    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity projectEntity;

    @ManyToOne
    @JoinColumn(name = "authority_code")
    private AuthorityEntity authorityEntity;

    private LocalDateTime createAt;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Setter
    @Embeddable
    @Builder
    public static class Pk implements Serializable {
        private Long projectId;
        private String accountId;
    }

    @PrePersist
    public void setCreateAt() {
        this.createAt = LocalDateTime.now();
    }
}
