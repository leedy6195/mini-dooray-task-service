package com.nhn.minidooray.taskapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.nhn.minidooray.taskapi.requestmapping")
@Getter
@Setter
public class RequestMappingProperties {
    private String prefix;

}