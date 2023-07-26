package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.ProjectAccountCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.ProjectAccountUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.AccountByProjectResponse;
import com.nhn.minidooray.taskapi.domain.response.ProjectAccountResponse;
import com.nhn.minidooray.taskapi.domain.response.ProjectByAccountResponse;
import com.nhn.minidooray.taskapi.entity.AuthorityEntity;
import com.nhn.minidooray.taskapi.entity.ProjectAccountEntity;
import com.nhn.minidooray.taskapi.entity.ProjectEntity;
import com.nhn.minidooray.taskapi.entity.ProjectStateEntity;
import com.nhn.minidooray.taskapi.enumerate.AuthorityType;
import com.nhn.minidooray.taskapi.enumerate.ProjectStateType;
import com.nhn.minidooray.taskapi.exception.AlreadyExistsException;
import com.nhn.minidooray.taskapi.exception.NotFoundException;
import com.nhn.minidooray.taskapi.repository.AuthorityRepository;
import com.nhn.minidooray.taskapi.repository.ProjectAccountRepository;
import com.nhn.minidooray.taskapi.repository.ProjectRepository;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectAccountServiceImplTest {
    @InjectMocks
    ProjectAccountServiceImpl projectAccountService;

    @Mock
    ProjectRepository projectRepository;
    @Mock
    ProjectAccountRepository projectAccountRepository;
    @Mock
    AuthorityRepository authorityRepository;


    @Test
    @DisplayName("프로젝트에 계정 추가")
    void createProjectAccount_o() {
        // given
        ProjectAccountCreateRequest projectAccountCreateRequest = ProjectAccountCreateRequest.builder()
                .accountId("test member")
                .authorityCode(AuthorityType.codeOf(AuthorityType.관리자))
                .build();

        when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ProjectEntity.builder()
                .projectStateEntity(ProjectStateEntity.builder().projectStateType(ProjectStateType.활성).build())
                .name("test project")
                .id(1L)
                .createAt(LocalDateTime.now())
                .build()));
        when(authorityRepository.findById(Mockito.anyString())).thenReturn(Optional.of(AuthorityEntity.builder()
                .code(AuthorityType.codeOf(AuthorityType.관리자))
                .authorityType(AuthorityType.관리자)
                .createAt(LocalDateTime.now())
                .build()));
        when(projectAccountRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        when(projectAccountRepository.save(Mockito.any(ProjectAccountEntity.class))).thenReturn(ProjectAccountEntity.builder()
                .pk(ProjectAccountEntity.Pk.builder()
                        .projectId(1L)
                        .accountId("test member")
                        .build())
                .projectEntity(ProjectEntity.builder()
                        .projectStateEntity(ProjectStateEntity.builder().projectStateType(ProjectStateType.활성).build())
                        .name("test project")
                        .id(1L)
                        .createAt(LocalDateTime.now())
                        .build())
                .authorityEntity(AuthorityEntity.builder()
                        .code(AuthorityType.codeOf(AuthorityType.관리자))
                        .authorityType(AuthorityType.관리자)
                        .createAt(LocalDateTime.now())
                        .build())
                .build());

        // when & then
        assertDoesNotThrow(() -> projectAccountService.createProjectAccount(Mockito.anyLong(), projectAccountCreateRequest));
    }

    @Test
    @DisplayName("프로젝트 멤버 생성 실패 (프로젝트가 존재하지 않음)")
    void createProjectAccount_x_projectNotExist() {
        // given
        ProjectAccountCreateRequest projectAccountCreateRequest = ProjectAccountCreateRequest.builder()
                .accountId("test member")
                .authorityCode(AuthorityType.codeOf(AuthorityType.관리자))
                .build();

        when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // when & then
        Assertions.assertThatThrownBy(() -> projectAccountService.createProjectAccount(Mockito.anyLong(), projectAccountCreateRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("project not found");
    }

    @Test
    @DisplayName("프로젝트 멤버 생성 실패 (권한이 존재하지 않음)")
    void createProjectAccount_x_authorityNotExist() {
        // given
        ProjectAccountCreateRequest projectAccountCreateRequest = ProjectAccountCreateRequest.builder()
                .accountId("test member")
                .authorityCode(AuthorityType.codeOf(AuthorityType.관리자))
                .build();

        when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ProjectEntity.builder()
                .projectStateEntity(ProjectStateEntity.builder().projectStateType(ProjectStateType.활성).build())
                .name("test project")
                .id(1L)
                .createAt(LocalDateTime.now())
                .build()));
        when(authorityRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        // when & then
        Assertions.assertThatThrownBy(() -> projectAccountService.createProjectAccount(Mockito.anyLong(), projectAccountCreateRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("authority not found");
    }

    @Test
    @DisplayName("프로젝트 멤버 생성 실패 (프로젝트에 멤버가 이미 존재함)")
    void createProjectAccount_x_accountAlreadyExist() {
        // given
        ProjectAccountCreateRequest projectAccountCreateRequest = ProjectAccountCreateRequest.builder()
                .accountId("test member")
                .authorityCode(AuthorityType.codeOf(AuthorityType.관리자))
                .build();

        when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ProjectEntity.builder()
                .projectStateEntity(ProjectStateEntity.builder().projectStateType(ProjectStateType.활성).build())
                .name("test project")
                .id(1L)
                .createAt(LocalDateTime.now())
                .build()));
        when(authorityRepository.findById(Mockito.anyString())).thenReturn(Optional.of(AuthorityEntity.builder()
                .code(AuthorityType.codeOf(AuthorityType.관리자))
                .authorityType(AuthorityType.관리자)
                .createAt(LocalDateTime.now())
                .build()));
        when(projectAccountRepository.findById(Mockito.any())).thenReturn(Optional.of(ProjectAccountEntity.builder()
                .pk(ProjectAccountEntity.Pk.builder()
                        .projectId(1L)
                        .accountId("test member")
                        .build())
                .projectEntity(ProjectEntity.builder()
                        .projectStateEntity(ProjectStateEntity.builder().projectStateType(ProjectStateType.활성).build())
                        .name("test project")
                        .id(1L)
                        .createAt(LocalDateTime.now())
                        .build())
                .authorityEntity(AuthorityEntity.builder()
                        .code(AuthorityType.codeOf(AuthorityType.관리자))
                        .authorityType(AuthorityType.관리자)
                        .createAt(LocalDateTime.now())
                        .build())
                .build()));

        // when & then
        Assertions.assertThatThrownBy(() -> projectAccountService.createProjectAccount(Mockito.anyLong(), projectAccountCreateRequest))
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessage("projectAccount already exists");
    }

    @Test
    @DisplayName("프로젝트 멤버 업데이트 성공")
    void updateProjectAccount_o() {
        when(projectAccountRepository.findById(Mockito.any())).thenReturn(Optional.of(ProjectAccountEntity.builder()
                .pk(ProjectAccountEntity.Pk.builder()
                        .projectId(1L)
                        .accountId("test member")
                        .build())
                .projectEntity(ProjectEntity.builder()
                        .projectStateEntity(ProjectStateEntity.builder().projectStateType(ProjectStateType.활성).build())
                        .name("test project")
                        .id(1L)
                        .createAt(LocalDateTime.now())
                        .build())
                .authorityEntity(AuthorityEntity.builder()
                        .code(AuthorityType.codeOf(AuthorityType.관리자))
                        .authorityType(AuthorityType.관리자)
                        .createAt(LocalDateTime.now())
                        .build())
                .build()));

        when(authorityRepository.findById(Mockito.anyString())).thenReturn(Optional.of(AuthorityEntity.builder()
                .code(AuthorityType.codeOf(AuthorityType.관리자))
                .authorityType(AuthorityType.관리자)
                .createAt(LocalDateTime.now())
                .build()));

        when(projectAccountRepository.save(Mockito.any(ProjectAccountEntity.class))).thenReturn(ProjectAccountEntity.builder()
                .pk(ProjectAccountEntity.Pk.builder()
                        .projectId(1L)
                        .accountId("test member")
                        .build())
                .projectEntity(ProjectEntity.builder()
                        .projectStateEntity(ProjectStateEntity.builder().projectStateType(ProjectStateType.활성).build())
                        .name("test project")
                        .id(1L)
                        .createAt(LocalDateTime.now())
                        .build())
                .authorityEntity(AuthorityEntity.builder()
                        .code(AuthorityType.codeOf(AuthorityType.관리자))
                        .authorityType(AuthorityType.관리자)
                        .createAt(LocalDateTime.now())
                        .build())
                .build());

        assertDoesNotThrow(() -> projectAccountService.updateProjectAccount(1L, "test member", ProjectAccountUpdateRequest.builder()
                .authorityCode(AuthorityType.codeOf(AuthorityType.관리자))
                .build()));
    }

    @Test
    @DisplayName("프로젝트 멤버 업데이트 실패(프로젝트 멤버가 존재하지 않음)")
    void updateProjectAccount_x_projectAccountNotExist() {
        when(projectAccountRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> projectAccountService.updateProjectAccount(1L, "test member", ProjectAccountUpdateRequest.builder()
                        .authorityCode(AuthorityType.codeOf(AuthorityType.관리자))
                        .build()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("projectAccount not found");
    }

    @Test
    @DisplayName("프로젝트 멤버 업데이트 실패(변경하려는 권한이 존재하지 않음)")
    void updateProjectAccount_x_authorityNotExist() {

        when(projectAccountRepository.findById(Mockito.any())).thenReturn(Optional.of(ProjectAccountEntity.builder()
                .pk(ProjectAccountEntity.Pk.builder()
                        .projectId(1L)
                        .accountId("test member")
                        .build())
                .projectEntity(ProjectEntity.builder()
                        .projectStateEntity(ProjectStateEntity.builder().projectStateType(ProjectStateType.활성).build())
                        .name("test project")
                        .id(1L)
                        .createAt(LocalDateTime.now())
                        .build())
                .authorityEntity(AuthorityEntity.builder()
                        .code(AuthorityType.codeOf(AuthorityType.관리자))
                        .authorityType(AuthorityType.관리자)
                        .createAt(LocalDateTime.now())
                        .build())
                .build()));

        when(authorityRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> projectAccountService.updateProjectAccount(1L, "test member", ProjectAccountUpdateRequest.builder()
                        .authorityCode(AuthorityType.codeOf(AuthorityType.관리자))
                        .build()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("authority not found");
    }

    @Test
    @DisplayName("프로젝트 멤버 삭제 성공")
    void deleteProjectAccount_o() {
        when(projectAccountRepository.findById(Mockito.any())).thenReturn(Optional.of(ProjectAccountEntity.builder()
                .pk(ProjectAccountEntity.Pk.builder()
                        .projectId(1L)
                        .accountId("test member")
                        .build())
                .projectEntity(ProjectEntity.builder()
                        .projectStateEntity(ProjectStateEntity.builder().projectStateType(ProjectStateType.활성).build())
                        .name("test project")
                        .id(1L)
                        .createAt(LocalDateTime.now())
                        .build())
                .authorityEntity(AuthorityEntity.builder()
                        .code(AuthorityType.codeOf(AuthorityType.관리자))
                        .authorityType(AuthorityType.관리자)
                        .createAt(LocalDateTime.now())
                        .build())
                .build()));

        assertDoesNotThrow(() -> projectAccountService.deleteProjectAccount(1L, "test member"));
    }

    @Test
    @DisplayName("프로젝트 멤버 삭제 실패(프로젝트 멤버가 존재하지 않음)")
    void deleteProjectAccount_x_projectAccountNotExist() {
        when(projectAccountRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> projectAccountService.deleteProjectAccount(1L, "test member"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("projectAccount not found");
    }

    @Test
    @DisplayName("프로젝트 전체멤버 조회 성공")
    void getAccountsByProject() {
        AccountByProjectResponse expected = AccountByProjectResponse.builder().projectName("test project")
                .accountId("test member")
                .authorityCode(AuthorityType.codeOf(AuthorityType.관리자))
                .authority(AuthorityType.관리자)
                .projectId(1L)
                .build();

        when(projectAccountRepository.findAccountsByProjectId(1L, PageRequest.of(0, 10)))
                .thenReturn(new PageImpl<>
                        (Collections.singletonList(
                                AccountByProjectResponse.builder()
                                        .projectId(1L)
                                        .projectName("test project")
                                        .accountId("test member")
                                        .authorityCode("01")
                                        .authority(AuthorityType.관리자)
                                        .build())));

        Page<AccountByProjectResponse> actual = projectAccountService.getAccountsByProject(1L, PageRequest.of(0, 10));
        Assertions.assertThat(actual.getContent().get(0)).isEqualTo(expected);
    }

    @Test
    @DisplayName("프로젝트 멤버 권한 조회 성공")
    void getAccountByProject_o() {
        when(projectAccountRepository.findByPk(
                ProjectAccountEntity.Pk.builder()
                        .projectId(1L)
                        .accountId("test member")
                        .build()))
                .thenReturn(Optional.of(() -> "01"));

        ProjectAccountResponse actual = projectAccountService.getAccountByProject(1L, "test member");
        Assertions.assertThat(actual.getAuthorityEntityCode()).isEqualTo("01");
    }

    @Test
    @DisplayName("프로젝트 멤버 권한 조회 실패(프로젝트 멤버가 존재하지 않음)")
    void getAccountByProject_x() {
        when(projectAccountRepository.findByPk(
                ProjectAccountEntity.Pk.builder()
                        .projectId(1L)
                        .accountId("test member")
                        .build()))
                .thenReturn(Optional.empty());


        Assertions.assertThatThrownBy(() -> projectAccountService.getAccountByProject(1L, "test member"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("ProjectAccountResponse not found");
    }
}