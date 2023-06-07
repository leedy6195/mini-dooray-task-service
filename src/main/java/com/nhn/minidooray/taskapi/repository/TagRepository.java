package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.domain.response.TagDto;
import com.nhn.minidooray.taskapi.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity, Long> {
}
