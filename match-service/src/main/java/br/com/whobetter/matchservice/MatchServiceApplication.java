package br.com.whobetter.matchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.resilience.annotation.EnableResilientMethods;

@SpringBootApplication
@EnableFeignClients
@EnableResilientMethods
public class MatchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MatchServiceApplication.class, args);
    }

}
