package com.cvte.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import utils.IdWorker;

@SpringBootApplication
public class mongodbApplication {

    public static void main(String[] args) {

        SpringApplication.run(mongodbApplication.class);
    }

    @Bean
    public IdWorker idWorker(){
        return IdWorker.getFlowIdWorkerInstance();
    }
}
