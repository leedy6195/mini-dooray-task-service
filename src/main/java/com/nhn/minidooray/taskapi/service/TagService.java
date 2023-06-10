package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.TagCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.TagUpdateRequest;

public interface TagService {
    Long createTag(Long projectId, TagCreateRequest tagCreateRequest);
    Long updateTag(Long projectId, Long tagId, TagUpdateRequest tagUpdateRequest);

    void deleteTag(Long projectId, Long tagId);
}
