package br.com.whobetter.scoringservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.resilience.annotation.EnableResilientMethods;

@SpringBootApplication
@EnableFeignClients
@EnableResilientMethods
public class ScoringServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScoringServiceApplication.class, args);
    }

}
