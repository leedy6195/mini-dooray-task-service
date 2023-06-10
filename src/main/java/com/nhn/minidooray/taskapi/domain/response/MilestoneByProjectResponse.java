package com.nhn.minidooray.taskapi.domain.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class MilestoneByProjectResponse {
    private Long projectId;
    private Long milestoneId;
    private String milestoneName;
    private LocalDate startDate;
    private LocalDate endDate;
}
