package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.entity.AuthorityEntity;
import com.nhn.minidooray.taskapi.enumerate.AuthorityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, String> {
    Optional<AuthorityEntity> findByAuthorityType(AuthorityType authorityName);
}
