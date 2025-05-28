package com.sam.publish_ride_service;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class PublishRideServiceApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(PublishRideServiceApplication.class)
                .web(WebApplicationType.NONE)  // <-- Prevents web server from starting
                .run(args);
    }
}
