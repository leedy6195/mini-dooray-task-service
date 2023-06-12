package com.nhn.minidooray.taskapi.domain.request;

import com.nhn.minidooray.taskapi.entity.MilestoneEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MilestoneCreateRequest {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
}
