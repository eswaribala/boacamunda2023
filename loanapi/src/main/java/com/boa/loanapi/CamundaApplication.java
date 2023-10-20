package com.boa.loanapi;

import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@SpringBootApplication
@EnableProcessApplication("loanapi")
//@EnableProcessApplication()
//@Deployment(resources = "uploaddoc.bpmn")
//@ConditionalOnProperty(name = "camunda.bpm.enabled", havingValue = "false", matchIfMissing = true)
public class CamundaApplication {

  public static void main(String... args) {
    SpringApplication.run(CamundaApplication.class, args);
  }

}
