package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.TagCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.TagUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.TagByProjectResponse;
import com.nhn.minidooray.taskapi.domain.response.TagByTaskResponse;
import com.nhn.minidooray.taskapi.entity.*;
import com.nhn.minidooray.taskapi.enumerate.ProjectStateType;
import com.nhn.minidooray.taskapi.exception.NotFoundException;
import com.nhn.minidooray.taskapi.repository.ProjectRepository;
import com.nhn.minidooray.taskapi.repository.TagRepository;
import com.nhn.minidooray.taskapi.repository.TaskRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @InjectMocks
    TagServiceImpl tagService;
    @Mock
    ProjectRepository projectRepository;
    @Mock
    TagRepository tagRepository;
    @Mock
    TaskRepository taskRepository;

    @Test
    @DisplayName("태그 생성 성공")
    void createTag_o() {
        // given
        TagCreateRequest tagCreateRequest = TagCreateRequest.builder()
                .name("test tag")
                .build();

        when(projectRepository.findById(anyLong())).thenReturn(Optional.ofNullable(ProjectEntity.builder()
                .projectStateEntity(ProjectStateEntity.builder()
                        .projectStateType(ProjectStateType.활성)
                        .build())
                .name("test project")
                .id(1L)
                .createAt(LocalDateTime.now())
                .build()));

        when(tagRepository.save(any(TagEntity.class))).thenReturn(
                TagEntity.builder()
                        .name("test tag")
                        .id(1L)
                        .build());

        // when
        Long tagId = tagService.createTag(1L, tagCreateRequest);

        // then
        Assertions.assertThat(tagId).isEqualTo(1L);
    }

    @Test
    @DisplayName("태그 생성 실패(프로젝트가 없는 경우)")
    void createTag_x() {
        // given
        TagCreateRequest tagCreateRequest = TagCreateRequest.builder()
                .name("test tag")
                .build();

        when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> tagService.createTag(1L, tagCreateRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("project not found");
    }

    @Test
    @DisplayName("태그 업데이트 성공")
    void updateTag_o() {
        // given
        TagUpdateRequest tagUpdateRequest = TagUpdateRequest.builder()
                .name("test tag")
                .build();

        when(projectRepository.findById(anyLong())).thenReturn(Optional.ofNullable(ProjectEntity.builder()
                .projectStateEntity(ProjectStateEntity.builder()
                        .projectStateType(ProjectStateType.활성)
                        .build())
                .name("test project")
                .id(1L)
                .createAt(LocalDateTime.now())
                .build()));

        when(tagRepository.findByIdAndProjectEntity(anyLong(), any(ProjectEntity.class))).thenReturn(Optional.ofNullable(TagEntity.builder()
                .name("test tag")
                .id(1L)
                .build()));


        // when
        Long actual = tagService.updateTag(1L, 1L, tagUpdateRequest);
        Assertions.assertThat(actual).isEqualTo(1L);
    }

    @Test
    @DisplayName("태그 업데이트 실패 (프로젝트가 없는 경우)")
    void updateTag_x_projectNotExist() {
        // given
        TagUpdateRequest tagUpdateRequest = TagUpdateRequest.builder()
                .name("test tag")
                .build();

        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        Assertions.assertThatThrownBy(() -> tagService.updateTag(1L, 1L, tagUpdateRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("project not found");
    }

    @Test
    @DisplayName("태그 업데이트 실패 (태그가 없는 경우)")
    void updateTag_x_tagNotExist() {
        // given
        TagUpdateRequest tagUpdateRequest = TagUpdateRequest.builder()
                .name("test tag")
                .build();

        when(projectRepository.findById(anyLong())).thenReturn(Optional.ofNullable(ProjectEntity.builder()
                .projectStateEntity(ProjectStateEntity.builder()
                        .projectStateType(ProjectStateType.활성)
                        .build())
                .name("test project")
                .id(1L)
                .createAt(LocalDateTime.now())
                .build()));

        when(tagRepository.findByIdAndProjectEntity(anyLong(), any(ProjectEntity.class))).thenReturn(Optional.empty());

        // when & then
        Assertions.assertThatThrownBy(() -> tagService.updateTag(1L, 1L, tagUpdateRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("tag not found");
    }


    @Test
    @DisplayName("태그 삭제 성공")
    void deleteTag_o() {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.ofNullable(ProjectEntity.builder()
                .projectStateEntity(ProjectStateEntity.builder()
                        .projectStateType(ProjectStateType.활성)
                        .build())
                .name("test project")
                .id(1L)
                .createAt(LocalDateTime.now())
                .build()));

        when(tagRepository.findByIdAndProjectEntity(anyLong(), any(ProjectEntity.class))).thenReturn(Optional.ofNullable(TagEntity.builder()
                .name("test tag")
                .id(1L)
                .build()));

        doNothing().when(tagRepository).delete(any(TagEntity.class));
        assertDoesNotThrow(() -> tagService.deleteTag(1L, 1L));
    }

    @Test
    @DisplayName("태그 삭제 실패 (프로젝트가 없는 경우)")
    void deleteTag_x_projectNotExist() {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> tagService.deleteTag(1L, 1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("project not found");
    }

    @Test
    @DisplayName("태그 삭제 실패 (태그가 없는 경우)")
    void deleteTag_x_tagNotExist() {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.ofNullable(ProjectEntity.builder()
                .projectStateEntity(ProjectStateEntity.builder()
                        .projectStateType(ProjectStateType.활성)
                        .build())
                .name("test project")
                .id(1L)
                .createAt(LocalDateTime.now())
                .build()));

        when(tagRepository.findByIdAndProjectEntity(anyLong(), any(ProjectEntity.class))).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> tagService.deleteTag(1L, 1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("tag not found");
    }

    @Test
    @DisplayName("프로젝트의 태그 조회 성공")
    void findTagsByProjectId() {
        TagByProjectResponse expected = TagByProjectResponse.builder()
                .projectId(1L)
                .tagId(1L)
                .tagName("test tag")
                .build();

        when(projectRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(
                        ProjectEntity.builder()
                                .id(1L)
                                .name("test project")
                                .projectStateEntity(ProjectStateEntity.builder()
                                        .projectStateType(ProjectStateType.활성)
                                        .build())
                                .build()));

        when(tagRepository.findTagsByProjectId(1L, PageRequest.of(0, 10)))
                .thenReturn(new PageImpl<>(Collections.singletonList(
                        TagByProjectResponse.builder()
                                .projectId(1L)
                                .tagId(1L)
                                .tagName("test tag")
                                .build())));
        // when
        Page<TagByProjectResponse> actual = tagService.findTagsByProjectId(1L, PageRequest.of(0, 10));

        // then
        Assertions.assertThat(actual.getContent().get(0)).isEqualTo(expected);
    }

    @Test
    @DisplayName("프로젝트의 태그 조회 실패 (프로젝트가 없는 경우)")
    void findTagsByProjectId_x() {
        TagByProjectResponse expected = TagByProjectResponse.builder()
                .projectId(1L)
                .tagId(1L)
                .tagName("test tag")
                .build();

        when(projectRepository.findById(Mockito.any()))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> tagService.findTagsByProjectId(1L, PageRequest.of(0, 10)))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("project not found");
    }

    @Test
    @DisplayName("태스크에 걸린 태그 조회 성공")
    void findTagsByTaskId_o() {
        // given
        TagByTaskResponse expected = TagByTaskResponse.builder()
                .taskId(1L)
                .tagId(1L)
                .tagName("test tag")
                .build();

        ProjectStateEntity projectState = ProjectStateEntity.builder()
                .code("0001")
                .projectStateType(ProjectStateType.활성)
                .build();
        ProjectEntity project = ProjectEntity.builder()
                .projectStateEntity(projectState)
                .name("test project").build();
        MilestoneEntity milestone = MilestoneEntity.builder()
                .projectEntity(project)
                .name("test milestone").build();
        TaskEntity task = TaskEntity.builder()
                .projectEntity(project)
                .milestoneEntity(milestone)
                .writerId("test writer")
                .title("test task")
                .build();

        when(taskRepository.findById(anyLong())).thenReturn(
                Optional.of(task)
        );

        when(tagRepository.findTagsByTaskId(anyLong(), any(Pageable.class))).thenReturn(new PageImpl<>(Collections.singletonList(
                TagByTaskResponse.builder()
                        .taskId(1L)
                        .tagId(1L)
                        .tagName("test tag")
                        .build())));
        // when
        Page<TagByTaskResponse> actual = tagService.findTagsByTaskId(1L, PageRequest.of(0, 10));

        // then
        Assertions.assertThat(actual.getContent().get(0)).isEqualTo(expected);
    }

    @Test
    @DisplayName("태스크에 걸린 태그 조회 실패 (태스크가 없는 경우)")
    void findTagsByTaskId_x() {
        // given
        TagByTaskResponse expected = TagByTaskResponse.builder()
                .taskId(1L)
                .tagId(1L)
                .tagName("test tag")
                .build();

        when(taskRepository.findById(anyLong())).thenReturn(
                Optional.empty()

        );
        // when & then
        Assertions.assertThatThrownBy(() -> tagService.findTagsByTaskId(1L, PageRequest.of(0, 10)))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("task not found");
    }
}