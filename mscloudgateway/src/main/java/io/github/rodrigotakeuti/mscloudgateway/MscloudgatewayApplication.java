package io.github.rodrigotakeuti.mscloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@SpringBootApplication
@EnableDiscoveryClient
public class MscloudgatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MscloudgatewayApplication.class, args);
    }




}
