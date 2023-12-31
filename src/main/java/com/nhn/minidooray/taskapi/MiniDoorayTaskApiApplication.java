package com.nhn.minidooray.taskapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ConfigurationPropertiesScan("com.nhn.minidooray.taskapi.config")
public class MiniDoorayTaskApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniDoorayTaskApiApplication.class, args);
    }

}
