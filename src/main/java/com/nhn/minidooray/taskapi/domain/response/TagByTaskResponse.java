package com.nhn.minidooray.taskapi.domain.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TagByTaskResponse {
    private Long taskId;
    private Long tagId;
    private String tagName;
}
