package com.nhn.minidooray.taskapi.service;

import com.nhn.minidooray.taskapi.domain.request.CommentCreateRequest;
import com.nhn.minidooray.taskapi.domain.request.CommentUpdateRequest;
import com.nhn.minidooray.taskapi.domain.response.CommentByTaskResponse;
import com.nhn.minidooray.taskapi.entity.CommentEntity;
import com.nhn.minidooray.taskapi.entity.TaskEntity;
import com.nhn.minidooray.taskapi.repository.CommentRepository;
import com.nhn.minidooray.taskapi.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    public CommentServiceImplTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createComment_withParentComment_shouldCreateCommentWithParent() {
        // given
        Long taskId = 1L;
        CommentCreateRequest commentCreateRequest = CommentCreateRequest.builder()
                .content("New comment")
                .parentId(2L)
                .writerId("user123")
                .build();

        CommentEntity parentComment = CommentEntity.builder()
                .id(2L)
                .writerId("user123")
                .content("Parent comment")
                .build();

        TaskEntity taskEntity = TaskEntity.builder()
                .id(taskId)
                .title("Task 1")
                .build();

        when(commentRepository.findById(commentCreateRequest.getParentId()))
                .thenReturn(Optional.of(parentComment));
        when(taskRepository.findById(taskId))
                .thenReturn(Optional.of(taskEntity));
        when(commentRepository.save(any(CommentEntity.class)))
                .thenAnswer(invocation -> {
                    CommentEntity savedComment = invocation.getArgument(0);
                    return savedComment;
                });

        // when
        Long commentId = commentService.createComment(taskId, commentCreateRequest);

        // then
        assertNotNull(commentId);
        assertEquals(3L, commentId);
        verify(commentRepository, times(1)).save(any(CommentEntity.class));
    }

    @Test
    void updateComment_existingComment_shouldUpdateComment() {
        // given
        Long commentId = 1L;
        CommentUpdateRequest commentUpdateRequest = CommentUpdateRequest.builder()
                .content("Updated comment")
                .build();

        CommentEntity existingComment = CommentEntity.builder()
                .id(commentId)
                .content("Old comment")
                .build();

        when(commentRepository.findById(commentId))
                .thenReturn(Optional.of(existingComment));
        when(commentRepository.save(any(CommentEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Long updatedCommentId = commentService.updateComment(commentId, commentUpdateRequest);

        // then
        assertNotNull(updatedCommentId);
        assertEquals(commentId, updatedCommentId);
        assertEquals(commentUpdateRequest.getContent(), existingComment.getContent());
        verify(commentRepository, times(1)).save(any(CommentEntity.class));
    }

    @Test
    void deleteComment_existingComment_shouldDeleteComment() {
        // given
        Long commentId = 1L;
        CommentEntity existingComment = CommentEntity.builder()
                .id(commentId)
                .content("Existing comment")
                .build();

        when(commentRepository.findById(commentId))
                .thenReturn(Optional.of(existingComment));

        // when
        commentService.deleteComment(commentId);

        // then
        verify(commentRepository, times(1)).delete(existingComment);
    }

    @Test
    void getCommentsByTaskId_existingTaskId_shouldReturnCommentsPage() {
        // given
        Long taskId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        TaskEntity existingTask = TaskEntity.builder()
                .id(taskId)
                .title("Task 1")
                .build();

        when(taskRepository.findById(taskId))
                .thenReturn(Optional.of(existingTask));
        when(commentRepository.findCommentsByTaskId(taskId, pageable))
                .thenReturn(somePageOfComments());

        // when
        Page<CommentByTaskResponse> commentsPage = commentService.getCommentsByTaskId(taskId, pageable);

        // then
        assertNotNull(commentsPage);
        // Perform assertions on the commentsPage as needed
        // ...
        verify(taskRepository, times(1)).findById(taskId);
        verify(commentRepository, times(1)).findCommentsByTaskId(taskId, pageable);
    }

    // Utility method to create a Page object with some dummy comments
    private Page<CommentByTaskResponse> somePageOfComments() {
        List<CommentByTaskResponse> commentsList = new ArrayList<>();
        // Add some comments to the list
        // ...
        return new PageImpl<>(commentsList);
    }
}