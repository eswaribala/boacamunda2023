package com.boa.camundaconsoleproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

	

}