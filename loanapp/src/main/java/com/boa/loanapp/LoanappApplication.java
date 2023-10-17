package com.boa.loanapp;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SpringBootApplication
@Slf4j
@Deployment(resources = "classpath*:/processes/**/*.bpmn")
public class LoanappApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanappApplication.class, args);
    }

    @JobWorker(name = "random_number_task",autoComplete = true)
    public Map<String,Object> handleRandomNumberGenerator(final JobClient jobClient, final ActivatedJob activatedJob){

        Map<String, Object> map=new HashMap<String,Object>();
        map.put("loanNo", new Random().nextInt(1000000));

        jobClient.newCompleteCommand(activatedJob.getKey())
                .variables(map)
                .send()
                .exceptionally(throwable -> {
                    throw new RuntimeException("Could not complete job " + jobClient, throwable);
                });
        log.info("job done...");
        return map;
    }



}
