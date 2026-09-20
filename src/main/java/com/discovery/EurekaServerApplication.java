package com.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
        System.out.println("==========================================");
        System.out.println("77 EUREKA SERVER RUNNING ON 8761 - TIER 8");
        System.out.println("Dashboard: http://localhost:8761");
        System.out.println("==========================================");
    }
}