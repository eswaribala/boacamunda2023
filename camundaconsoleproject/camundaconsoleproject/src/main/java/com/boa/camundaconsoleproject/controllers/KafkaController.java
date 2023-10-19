package com.boa.camundaconsoleproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.camunda.zeebe.client.ZeebeClient;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/messages")
@Slf4j
public class KafkaController {

	@Autowired
    private ZeebeClient zeebeClient;

   @PostMapping("/v1.0/")
    public void startKafkaBPMNProcess(){

       log.info("Starting process `" + ProcessConstants.KAFKA_BPMN_PROCESS_ID);
      
       zeebeClient
               .newCreateInstanceCommand()
               .bpmnProcessId(ProcessConstants.KAFKA_BPMN_PROCESS_ID)
               .latestVersion()
              // .variables(variables)
               .send();



   }
}
