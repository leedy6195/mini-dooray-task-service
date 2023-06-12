package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.MilestoneCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.MilestoneUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.MilestoneByProjectResponse;
import com.nhn.minidooray.taskapi.entity.MilestoneEntity;
import com.nhn.minidooray.taskapi.entity.ProjectEntity;
import com.nhn.minidooray.taskapi.entity.ProjectStateEntity;
import com.nhn.minidooray.taskapi.enumerate.ProjectStateType;
import com.nhn.minidooray.taskapi.exception.NotFoundException;
import com.nhn.minidooray.taskapi.repository.MilestoneRepository;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MilestoneServiceImplTest {
    @InjectMocks
    MilestoneServiceImpl milestoneService;
    @Mock
    ProjectRepository projectRepository;
    @Mock
    MilestoneRepository milestoneRepository;

    @Test
    @DisplayName("마일스톤 생성 성공")
    void createMilestone_o() {
        // given
        MilestoneCreateRequest milestoneCreateRequest = MilestoneCreateRequest.builder()
                .name("test milestone")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();

        when(projectRepository.findById(anyLong())).thenReturn(Optional.ofNullable(ProjectEntity.builder()
                .projectStateEntity(ProjectStateEntity.builder()
                        .projectStateType(ProjectStateType.활성)
                        .build())
                .name("test project")
                .id(1L)
                .createAt(LocalDateTime.now())
                .build()));
        when(milestoneRepository.save(any(MilestoneEntity.class))).thenReturn(
                MilestoneEntity.builder()
                        .projectEntity(ProjectEntity.builder()
                                .projectStateEntity(ProjectStateEntity.builder().projectStateType(ProjectStateType.활성).build())
                                .name("test project")
                                .id(1L)
                                .createAt(LocalDateTime.now())
                                .build())
                        .id(1L)
                        .name("test milestone")
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now())
                        .build());

        // when
        Long milestoneId = milestoneService.createMilestone(1L, milestoneCreateRequest);

        // then
        Assertions.assertThat(milestoneId).isEqualTo(1L);
    }

    @Test
    @DisplayName("마일스톤 생성 실패 (프로젝트가 없는 경우)")
    void createMilestone_x() {
        // given
        MilestoneCreateRequest milestoneCreateRequest = MilestoneCreateRequest.builder()
                .name("test milestone")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();

        when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        Assertions.assertThatThrownBy(() -> milestoneService.createMilestone(1L, milestoneCreateRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("project not found");
    }

    @Test
    @DisplayName("마일스톤 업데이트 성공")
    void updateMilestone_o() {
        // given
        MilestoneUpdateRequest milestoneUpdateRequest = MilestoneUpdateRequest.builder()
                .name("update milestone")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();

        when(milestoneRepository.findById(1L)).thenReturn(Optional.of(MilestoneEntity.builder()
                .projectEntity(ProjectEntity.builder()
                        .projectStateEntity(ProjectStateEntity.builder().projectStateType(ProjectStateType.활성).build())
                        .name("test project")
                        .id(1L)
                        .build())
                .id(1L)
                .name("test milestone")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build()));

        // when
        Long actual = milestoneService.updateMilestone(1L, milestoneUpdateRequest);
        Assertions.assertThat(actual).isEqualTo(1L);
    }

    @Test
    @DisplayName("마일스톤 업데이트 실패 (마일스톤이 없는 경우)")
    void updateMilestone_x() {
        // given
        MilestoneUpdateRequest milestoneUpdateRequest = MilestoneUpdateRequest.builder()
                .name("update milestone")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();

        when(milestoneRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        Assertions.assertThatThrownBy(() -> milestoneService.updateMilestone(1L, milestoneUpdateRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("milestone not found");
    }

    @Test
    @DisplayName("마일스톤 삭제 성공")
    void deleteMilestone_o() {
        when(milestoneRepository.findById(1L)).thenReturn(Optional.of(MilestoneEntity.builder()
                .projectEntity(ProjectEntity.builder()
                        .projectStateEntity(ProjectStateEntity.builder().projectStateType(ProjectStateType.활성).build())
                        .name("test project")
                        .id(1L)
                        .build())
                .id(1L)
                .name("test milestone")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build()));

        doNothing().when(milestoneRepository).delete(any(MilestoneEntity.class));
        assertDoesNotThrow(() -> milestoneService.deleteMilestone(1L));
    }

    @Test
    @DisplayName("마일스톤 삭제 실패 (마일스톤이 없는 경우)")
    void deleteMilestone_x() {
        when(milestoneRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> milestoneService.deleteMilestone(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("milestone not found");
    }


    @Test
    @DisplayName("마일스톤 조회 성공")
    void findMilestonesByProject_o() {

        // given
        MilestoneByProjectResponse expected = MilestoneByProjectResponse.builder().projectId(1L).build();

        when(projectRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(
                        ProjectEntity.builder()
                                .id(1L)
                                .name("test project")
                                .build()));

        when(milestoneRepository.findMilestoneByProject(1L, PageRequest.of(0, 10))).thenReturn(
                new PageImpl<>(Collections.singletonList(MilestoneByProjectResponse.builder().projectId(1L).build()))
        );

        // when
        Page<MilestoneByProjectResponse> actual = milestoneService.findMilestonesByProject(1L, PageRequest.of(0, 10));

        // then
        Assertions.assertThat(actual.getContent().get(0)).isEqualTo(expected);
    }

    @Test
    @DisplayName("마일스톤 조회 실패 (프로젝트가 없는 경우)")
    void findMilestonesByProject_x() {
        when(projectRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> milestoneService.findMilestonesByProject(1L, PageRequest.of(0, 10)))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("project not found");
    }
}