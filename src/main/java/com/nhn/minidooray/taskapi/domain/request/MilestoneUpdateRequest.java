package com.nhn.minidooray.taskapi.domain.request;

import lombok.Data;

@Data
public class MilestoneUpdateRequest {
    private String name;
    private String startDate;
    private String endDate;
}
