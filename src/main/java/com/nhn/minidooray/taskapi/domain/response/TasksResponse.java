package com.nhn.minidooray.taskapi.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public interface TasksResponse {

    @JsonProperty("projectId")
    Long getProjectEntityId();

    Long getId();

    String getTitle();

    String getContent();

    String getWriterId();

    LocalDateTime getCreateAt();
}
