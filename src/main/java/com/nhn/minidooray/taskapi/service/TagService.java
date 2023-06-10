package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.TagCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.TagUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.TagByProjectResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {
    Long createTag(Long projectId, TagCreateRequest tagCreateRequest);
    Long updateTag(Long projectId, Long tagId, TagUpdateRequest tagUpdateRequest);
    void deleteTag(Long projectId, Long tagId);
    Page<TagByProjectResponse> findTagsByProjectId(Long projectId, Pageable pageable);
}
