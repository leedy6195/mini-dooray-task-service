package com.nhn.minidooray.taskapi.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TagByTaskResponse {
    private Long taskId;
    private Long tagId;
    private String tagName;
}
