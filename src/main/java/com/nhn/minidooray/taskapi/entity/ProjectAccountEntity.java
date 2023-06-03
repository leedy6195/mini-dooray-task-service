package com.nhn.minidooray.taskapi.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "project_account")
@NoArgsConstructor
@Getter
@Setter
public class ProjectAccountEntity {
    @EmbeddedId
    private Pk pk;

    @MapsId("projectId")
    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity projectEntity;

    @MapsId("authorityCode")
    @ManyToOne
    @JoinColumn(name = "authority_code")
    private AuthorityEntity authorityEntity;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Setter
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "project_id")
        private Long projectId;

        @Column(name = "authority_code")
        private String authorityCode;
    }

    @Builder
    public ProjectAccountEntity(Pk pk, ProjectEntity projectEntity, AuthorityEntity authorityEntity, String accountId, LocalDateTime createAt) {
        this.pk = pk;
        this.projectEntity = projectEntity;
        this.authorityEntity = authorityEntity;
        this.accountId = accountId;
        this.createAt = createAt;
    }
}
