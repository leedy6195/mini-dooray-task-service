package com.nhn.minidooray.taskapi.domain.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MilestoneUpdateRequest {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
}
