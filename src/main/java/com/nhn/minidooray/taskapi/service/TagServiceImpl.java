package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.TagCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.TagUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.TagByProjectResponse;
import com.nhn.minidooray.taskapi.domain.response.TagByTaskResponse;
import com.nhn.minidooray.taskapi.entity.ProjectEntity;
import com.nhn.minidooray.taskapi.entity.TagEntity;
import com.nhn.minidooray.taskapi.exception.NotFoundException;
import com.nhn.minidooray.taskapi.repository.ProjectRepository;
import com.nhn.minidooray.taskapi.repository.TagRepository;
import com.nhn.minidooray.taskapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;
    private final TaskRepository taskRepository;

    @Override
    @Transactional
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
    @Transactional
    public Long updateTag(Long projectId, Long tagId, TagUpdateRequest tagUpdateRequest) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("project"));

        TagEntity tagEntity = tagRepository.findByIdAndProjectEntity(tagId, projectEntity)
                .orElseThrow(() -> new NotFoundException("tag"));

        tagEntity.update(tagUpdateRequest);
        return tagEntity.getId();
    }

    @Override
    @Transactional
    public void deleteTag(Long projectId, Long tagId) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("project"));
        TagEntity tagEntity = tagRepository.findByIdAndProjectEntity(tagId, projectEntity)
                .orElseThrow(() -> new NotFoundException("tag"));

        tagRepository.delete(tagEntity);
    }

    @Override
    public Page<TagByProjectResponse> findTagsByProjectId(Long projectId, Pageable pageable) {
        projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("project"));

        return tagRepository.findTagsByProjectId(projectId, pageable);
    }

    @Override
    public Page<TagByTaskResponse> findTagsByTaskId(Long taskId, Pageable pageable) {
        taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("task"));

        return tagRepository.findTagsByTaskId(taskId, pageable);
    }
}
