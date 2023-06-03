package com.nhn.minidooray.taskapi.entity;


import com.nhn.minidooray.taskapi.enums.AuthorityType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "authority")
@NoArgsConstructor
@Getter
@Setter
public class AuthorityEntity {
    @Id
    private String code;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private AuthorityType authorityType;

    @Column(name = "create_at")
    private LocalDateTime createAt;
}
