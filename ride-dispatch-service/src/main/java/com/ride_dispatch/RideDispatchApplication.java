package com.ride_dispatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class RideDispatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(RideDispatchApplication.class, args);
    }

}