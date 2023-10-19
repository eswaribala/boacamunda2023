package com.boa.camundaconsoleproject.controllers;

import java.time.Duration;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.camunda.zeebe.client.ZeebeClient;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
	
	@Autowired
    private ZeebeClient zeebeClient;

   @PostMapping("/v1.0/")
    public void startBPMNProcess(){

       log.info(
               "Starting process `" + ProcessConstants.BPMN_PROCESS_ID);
       /*zeebeClient.newDeployResourceCommand()
               .addResourceFromClasspath("processes/user.bpmn")
               .send()
               .join();*/

       zeebeClient
               .newCreateInstanceCommand()
               .bpmnProcessId(ProcessConstants.BPMN_PROCESS_ID)
               .latestVersion()
              // .variables(variables)
               .send();



   }

   @GetMapping("/v1.0/")
   public void triggerMessageEvent(){
      
     
    zeebeClient.newPublishMessageCommand().messageName("message_trigger_api_call")
    .correlationKey(String.valueOf(new Random().nextInt(10000)))
    .timeToLive(Duration.ofMinutes(2))
    .send()

    .exceptionally(throwable -> {
        throw new RuntimeException("Could not complete job " + zeebeClient, throwable);
    });
    log.info("Message published");

      



  }
  
}
