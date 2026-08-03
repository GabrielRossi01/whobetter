package br.com.whobetter.predictionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.resilience.annotation.EnableResilientMethods;

@SpringBootApplication
@EnableFeignClients
@EnableResilientMethods
public class PredictionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PredictionServiceApplication.class, args);
    }

}
