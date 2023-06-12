package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.ProjectCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.ProjectUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.ProjectByAccountResponse;
import com.nhn.minidooray.taskapi.entity.AuthorityEntity;
import com.nhn.minidooray.taskapi.entity.ProjectAccountEntity;
import com.nhn.minidooray.taskapi.entity.ProjectEntity;
import com.nhn.minidooray.taskapi.entity.ProjectStateEntity;
import com.nhn.minidooray.taskapi.enumerate.AuthorityType;
import com.nhn.minidooray.taskapi.enumerate.ProjectStateType;
import com.nhn.minidooray.taskapi.exception.NotFoundException;
import com.nhn.minidooray.taskapi.exception.ValidationFailedException;
import com.nhn.minidooray.taskapi.repository.AuthorityRepository;
import com.nhn.minidooray.taskapi.repository.ProjectAccountRepository;
import com.nhn.minidooray.taskapi.repository.ProjectRepository;
import com.nhn.minidooray.taskapi.repository.ProjectStateRepository;
import net.bytebuddy.asm.Advice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @InjectMocks
    ProjectServiceImpl projectService;
    @Mock
    ProjectRepository projectRepository;

    @Mock
    ProjectAccountRepository projectAccountRepository;

    @Mock
    AuthorityRepository authorityRepository;

    @Mock
    ProjectStateRepository projectStateRepository;

    @Test
    @DisplayName("프로젝트 생성 성공")
    void createProject_o() {
        // given
        ProjectCreateRequest projectCreateRequest =
                ProjectCreateRequest
                        .builder()
                        .projectName("test project")
                        .accountId("test account")
                        .build();

        when(authorityRepository.findByAuthorityType(AuthorityType.관리자))
                .thenReturn(Optional.of(AuthorityEntity.builder()
                        .code(AuthorityType.codeOf(AuthorityType.관리자))
                        .authorityType(AuthorityType.관리자)
                        .createAt(LocalDateTime.now())
                        .build()));
        when(projectStateRepository.findByProjectStateType(ProjectStateType.활성))
                .thenReturn(Optional.of(ProjectStateEntity.builder()
                        .code(ProjectStateType.codeOf(ProjectStateType.활성))
                        .projectStateType(ProjectStateType.활성)
                        .createAt(LocalDateTime.now())
                        .build()));

        when(projectRepository.save(Mockito.any(ProjectEntity.class)))
                .thenReturn(ProjectEntity.builder()
                        .projectStateEntity(ProjectStateEntity.builder().projectStateType(ProjectStateType.활성).build())
                        .name("test project")
                        .id(1L)
                        .createAt(LocalDateTime.now())
                        .build());

        when(projectAccountRepository.save(Mockito.any()))
                .thenReturn(null);

        // when
        Long actual = projectService.createProject(projectCreateRequest);

        // then
        Assertions.assertThat(actual).isEqualTo(1L);
    }

    @Test
    @DisplayName("프로젝트 생성 실패 (authority 미설정)")
    void createProject_x_authorityNotExist() {
        // given
        ProjectCreateRequest projectCreateRequest =
                ProjectCreateRequest
                        .builder()
                        .projectName("")
                        .accountId("test account")
                        .build();

        // when & then
        assertThatThrownBy(() -> {
            projectService.createProject(projectCreateRequest);
        }).isInstanceOf(NotFoundException.class).hasMessage("authority not found");
    }

    @Test
    @DisplayName("프로젝트 생성 실패 (projectState 미설정)")
    void createProject_x_projectStateNotExist() {
        // given
        ProjectCreateRequest projectCreateRequest =
                ProjectCreateRequest
                        .builder()
                        .projectName("")
                        .accountId("test account")
                        .build();

        when(authorityRepository.findByAuthorityType(AuthorityType.관리자))
                .thenReturn(Optional.of(AuthorityEntity.builder()
                        .code(AuthorityType.codeOf(AuthorityType.관리자))
                        .authorityType(AuthorityType.관리자)
                        .createAt(LocalDateTime.now())
                        .build()));

        // when & then
        assertThatThrownBy(() -> {
            projectService.createProject(projectCreateRequest);
        }).isInstanceOf(NotFoundException.class).hasMessage("projectState not found");
    }

    @Test
    @DisplayName("프로젝트 업데이트 성공")
    void updateProject_o() {
        // given
        ProjectUpdateRequest projectUpdateRequest =
                ProjectUpdateRequest
                        .builder()
                        .projectName("after")
                        .build();
        when(projectRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(
                        ProjectEntity.builder()
                                .id(1L)
                                .name("before")
                                .build()));
        when(projectStateRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(
                        ProjectStateEntity.builder()
                                .projectStateType(ProjectStateType.활성)
                                .build()));
        when(projectRepository.save(Mockito.any()))
                .thenReturn(ProjectEntity.builder()
                        .id(1L)
                        .build());
        // when
        Long actual = projectService.updateProject(1L, projectUpdateRequest);
        // then
        Assertions.assertThat(actual).isEqualTo(1L);
    }

    @Test
    @DisplayName("프로젝트 업데이트 실패 (project 미설정)")
    void updateProject_x_projectNotExist() {
        // given
        ProjectUpdateRequest projectUpdateRequest =
                ProjectUpdateRequest
                        .builder()
                        .projectName("after")
                        .build();

        // when & then
        assertThatThrownBy(() -> {
            projectService.updateProject(1L, projectUpdateRequest);
        }).isInstanceOf(NotFoundException.class).hasMessage("project not found");
    }

    @Test
    @DisplayName("프로젝트 업데이트 실패 (projectState 미설정)")
    void updateProject_x_projectStateNotExist() {
        // given
        ProjectUpdateRequest projectUpdateRequest =
                ProjectUpdateRequest
                        .builder()
                        .projectName("after")
                        .build();

        when(projectRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(
                        ProjectEntity.builder()
                                .id(1L)
                                .name("before")
                                .build()));

        // when & then
        assertThatThrownBy(() -> {
            projectService.updateProject(1L, projectUpdateRequest);
        }).isInstanceOf(NotFoundException.class).hasMessage("projectState not found");
    }


    @Test
    @DisplayName("프로젝트 삭제 성공")
    void deleteProject_o() {
        when(projectRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(
                        ProjectEntity.builder()
                                .id(1L)
                                .name("test project")
                                .build()));
        doNothing().when(projectRepository).delete(Mockito.any());
        assertDoesNotThrow(() -> projectService.deleteProject(1L));
    }

    @Test
    @DisplayName("프로젝트 삭제 실패 (해당 프로젝트 찾을 수 없음)")
    void deleteProject_x() {
        when(projectRepository.findById(Mockito.any()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            projectService.deleteProject(1L);
        }).isInstanceOf(NotFoundException.class).hasMessage("project not found");
    }


    @Test
    @DisplayName("프로젝트 조회 성공")
    void getProjectsByAccount_o() {
        // given
        when(projectAccountRepository.findAllByPk_AccountId("test", PageRequest.of(0, 10)))
                .thenReturn(new PageImpl<>(Collections.singletonList(
                        ProjectAccountEntity.builder()
                                .projectEntity(ProjectEntity.builder()
                                        .name("test project")
                                        .projectStateEntity(ProjectStateEntity.builder()
                                                .code(ProjectStateType.codeOf(ProjectStateType.활성))
                                                .build())
                                        .build())
                                .authorityEntity(AuthorityEntity.builder().code(AuthorityType.codeOf(AuthorityType.관리자)).authorityType(AuthorityType.관리자).build())
                                .pk(ProjectAccountEntity.Pk.builder()
                                        .projectId(1L)
                                        .accountId("test")
                                        .build())
                                .build()
                )));
        // when
        Page<ProjectByAccountResponse> actual = projectService.getProjectsByAccount("test", PageRequest.of(0, 10));
        // then
        Assertions.assertThat(actual.getContent().get(0)).isEqualTo(ProjectByAccountResponse.builder()
                        .accountId("test")
                .projectName("test project")
                .projectStateCode(ProjectStateType.codeOf(ProjectStateType.활성))
                .accountAuthority(AuthorityType.codeOf(AuthorityType.관리자))
                .build());
    }
}