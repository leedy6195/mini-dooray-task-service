package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.TagCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.TagUpdateRequest;
import com.nhn.minidooray.taskapi.entity.ProjectEntity;
import com.nhn.minidooray.taskapi.entity.TagEntity;
import com.nhn.minidooray.taskapi.exception.NotFoundException;
import com.nhn.minidooray.taskapi.repository.ProjectRepository;
import com.nhn.minidooray.taskapi.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;


    @Override
    public Long createTag(Long projectId, TagCreateRequest tagCreateRequest) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("project"));

        return tagRepository.save(TagEntity.builder()
                        .projectEntity(projectEntity)
                        .name(tagCreateRequest.getName())
                        .build())
                    .getId();
    }

    @Override
    public Long updateTag(Long tagId, TagUpdateRequest tagUpdateRequest) {
        TagEntity tagEntity = tagRepository.findById(tagId)
                .orElseThrow(() -> new NotFoundException("tag"));

        tagEntity.update(tagUpdateRequest);
        return tagEntity.getId();
    }

    @Override
    public void deleteTag(Long tagId) {
        TagEntity tagEntity = tagRepository.findById(tagId)
                .orElseThrow(() -> new NotFoundException("tag"));

        tagRepository.delete(tagEntity);
    }
}
