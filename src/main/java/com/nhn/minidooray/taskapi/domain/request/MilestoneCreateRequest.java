package com.nhn.minidooray.taskapi.domain.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MilestoneCreateRequest {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
}
