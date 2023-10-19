package com.boa.loanapp;

import ch.qos.logback.classic.joran.action.LoggerAction;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

@SpringBootApplication
@Slf4j
@Deployment(resources ={ ("classpath*:/processes/**/*.bpmn"),("classpath*:/decisions/**/*.dmn")})

public class LoanappApplication {

    public static void main(String[] args) {

        SpringApplication.run(LoanappApplication.class, args);
    }





}