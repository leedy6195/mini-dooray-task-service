package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.entity.AuthorityEntity;
import com.nhn.minidooray.taskapi.enums.AuthorityType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, String> {
    AuthorityEntity findByAuthorityType(AuthorityType authorityName);
}
