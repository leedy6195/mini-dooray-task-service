package com.nhn.minidooray.taskapi.domain.response;

import com.nhn.minidooray.taskapi.enumerate.AuthorityType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
public class AccountByProjectResponse {
    private Long projectId;
    private String projectName;
    private String accountId;
    private String authorityCode;
    private AuthorityType authority;

    public AccountByProjectResponse(Long projectId, String projectName, String accountId, String authorityCode, AuthorityType authority) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.accountId = accountId;
        this.authorityCode = authorityCode;
        this.authority = authority;
    }
}
