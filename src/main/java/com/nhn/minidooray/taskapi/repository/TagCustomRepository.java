package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.domain.response.TagByProjectResponse;
import com.nhn.minidooray.taskapi.entity.TagEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagCustomRepository {
    Page<TagByProjectResponse> findTagsByProjectId(Long projectId, Pageable pageable);
}
