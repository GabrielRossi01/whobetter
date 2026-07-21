package br.com.whobetter.predictionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PredictionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PredictionServiceApplication.class, args);
    }

}
