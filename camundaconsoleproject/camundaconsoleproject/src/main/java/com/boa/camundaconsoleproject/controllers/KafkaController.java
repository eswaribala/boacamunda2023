package com.boa.camundaconsoleproject.controllers;

import java.time.Duration;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boa.camundaconsoleproject.configurations.KafkaConsumerConfiguration;

import io.camunda.zeebe.client.ZeebeClient;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/messages")
@Slf4j
public class KafkaController {

	@Autowired
    private ZeebeClient zeebeClient;
	@Autowired
	private KafkaConsumerConfiguration kafkaConsumerConfiguration;
	

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
   
   @GetMapping("/v1.0/")
   public void triggerKafkaListenerMessageEvent(){
	   kafkaConsumerConfiguration.triggerMessageConsumption();
     
    zeebeClient.newPublishMessageCommand().messageName("message_receive")
    .correlationKey("1002")
    .timeToLive(Duration.ofMinutes(10))
    .send()

    .exceptionally(throwable -> {
        throw new RuntimeException("Could not complete job " + zeebeClient, throwable);
    });
    log.info("Message received");

      



  }
}
