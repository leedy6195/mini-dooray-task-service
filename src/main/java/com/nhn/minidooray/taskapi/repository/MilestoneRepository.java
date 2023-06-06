package com.nhn.minidooray.taskapi.repository;

import com.nhn.minidooray.taskapi.entity.MilestoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<MilestoneEntity, Long> {

}
