package com.nhn.minidooray.taskapi.domain.request;

import com.nhn.minidooray.taskapi.entity.ProjectEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class ProjectCreateRequest {
    @NotNull
    @NotEmpty
    private String accountId;
    @NotNull
    @NotEmpty
    private String projectName;
}