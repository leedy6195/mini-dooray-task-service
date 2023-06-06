package com.nhn.minidooray.taskapi.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.nhn.minidooray.taskapi")
@Getter
@Setter
public class ApiMessageProperties {
    private String createSuccMessage;
    private String updateSuccMessage;
    private String deleteSuccMessage;
    private String getSuccMessage;
}
