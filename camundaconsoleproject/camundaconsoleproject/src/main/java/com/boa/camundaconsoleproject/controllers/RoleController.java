package com.boa.camundaconsoleproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boa.camundaconsoleproject.configurations.KafkaConsumerConfiguration;

import io.camunda.zeebe.client.ZeebeClient;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/roles")
@Slf4j
public class RoleController {
	@Autowired
    private ZeebeClient zeebeClient;
	@Autowired
	private KafkaConsumerConfiguration kafkaConsumerConfiguration;
	

   @PostMapping("/v1.0/")
    public void startRoleBPMNProcess(){

       log.info("Starting process `" + ProcessConstants.ROLE_BPMN_PROCESS_ID);
      
       zeebeClient
               .newCreateInstanceCommand()
               .bpmnProcessId(ProcessConstants.ROLE_BPMN_PROCESS_ID)
               .latestVersion()
              // .variables(variables)
               .send();



   }
}
