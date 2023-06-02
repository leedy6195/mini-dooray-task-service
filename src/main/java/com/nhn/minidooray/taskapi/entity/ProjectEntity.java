package com.nhn.minidooray.taskapi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ProjectEntity {
    @Id
    Long id;

    String name;
}
