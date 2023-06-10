package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.domain.response.TagByProjectResponse;
import com.nhn.minidooray.taskapi.entity.ProjectEntity;
import com.nhn.minidooray.taskapi.entity.TagEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity, Long> {

    Optional<TagEntity> findByIdAndProjectEntity(Long id, ProjectEntity projectEntity);

    Page<TagByProjectResponse> findByProjectEntity_IdEquals(Long projectId, Pageable pageable);
}
