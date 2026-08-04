package com.routing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class NetworkRoutingApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetworkRoutingApplication.class, args);
    }
}
