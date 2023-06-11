package com.nhn.minidooray.taskapi.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface ProjectAccountResponse {

    @JsonProperty("code")
    String getAuthorityEntityCode();

}
