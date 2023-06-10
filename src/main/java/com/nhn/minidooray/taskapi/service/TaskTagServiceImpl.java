package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.TaskTagCreateRequest;
import com.nhn.minidooray.taskapi.entity.TagEntity;
import com.nhn.minidooray.taskapi.entity.TaskEntity;
import com.nhn.minidooray.taskapi.entity.TaskTagEntity;
import com.nhn.minidooray.taskapi.exception.AlreadyExistsException;
import com.nhn.minidooray.taskapi.exception.NotFoundException;
import com.nhn.minidooray.taskapi.repository.TagRepository;
import com.nhn.minidooray.taskapi.repository.TaskRepository;
import com.nhn.minidooray.taskapi.repository.TaskTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskTagServiceImpl implements TaskTagService {
    private final TaskTagRepository taskTagRepository;
    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;

    @Override
    public void createTaskTag(Long taskId, Long tagId) {
        taskTagRepository.findById(TaskTagEntity.Pk.builder().tagId(tagId).build()).ifPresent(taskTagEntity -> {
            throw new AlreadyExistsException("taskTag");
        });
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("task"));
        TagEntity tagEntity = tagRepository.findById(tagId)
                .orElseThrow(() -> new NotFoundException("tag"));

        taskTagRepository.save(
            TaskTagEntity.builder()
                    .pk(TaskTagEntity.Pk.builder()
                            .tagId(tagId)
                            .taskId(taskId)
                            .build())
                    .taskEntity(taskEntity)
                    .tagEntity(tagEntity)
                    .build());
    }

    @Override
    public void deleteTaskTag(Long taskId, Long tagId) {
        taskTagRepository.findById(TaskTagEntity.Pk.builder()
                .tagId(tagId)
                .taskId(taskId)
                .build()).orElseThrow(() -> new NotFoundException("taskTag"));
        taskTagRepository.deleteById(TaskTagEntity.Pk.builder().tagId(tagId).taskId(taskId).build());
    }
}
