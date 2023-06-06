package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagEntity, Long> {
}
