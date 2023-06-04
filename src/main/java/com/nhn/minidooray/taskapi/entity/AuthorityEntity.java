package com.nhn.minidooray.taskapi.entity;


import com.nhn.minidooray.taskapi.enumerate.AuthorityType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "authority")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthorityEntity {
    @Id
    private String code;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private AuthorityType authorityType;

    private LocalDateTime createAt;

    @PrePersist
    public void setCreateAt() {
        this.createAt = LocalDateTime.now();
    }
}
