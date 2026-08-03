package br.com.whobetter.rankingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.resilience.annotation.EnableResilientMethods;

@SpringBootApplication
@EnableFeignClients
@EnableResilientMethods
public class RankingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RankingServiceApplication.class, args);
    }

}
