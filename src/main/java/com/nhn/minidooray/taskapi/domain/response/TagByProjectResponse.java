package com.nhn.minidooray.taskapi.domain.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TagByProjectResponse {
    Long projectId;
    Long tagId;
    String tagName;
}