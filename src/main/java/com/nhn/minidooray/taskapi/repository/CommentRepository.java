package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
