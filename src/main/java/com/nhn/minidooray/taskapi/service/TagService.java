package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.TagCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.TagUpdateRequest;

public interface TagService {
    Long createTag(Long projectId, TagCreateRequest tagCreateRequest);
    Long updateTag(Long tagId, TagUpdateRequest tagUpdateRequest);

    void deleteTag(Long tagId);
}
