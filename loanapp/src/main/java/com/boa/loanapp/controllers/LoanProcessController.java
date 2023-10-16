package com.boa.loanapp.controllers;

import io.camunda.zeebe.client.ZeebeClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/loans")
@Slf4j
public class LoanProcessController {

    @Autowired
    private ZeebeClient zeebeClient;

   @PostMapping("/v1.0/")
    public void startBPMNProcess(@RequestBody Map<String, Object> variables){

       log.info(
               "Starting process `" + ProcessConstants.BPMN_PROCESS_ID + "` with variables: " + variables);

       zeebeClient
               .newCreateInstanceCommand()
               .bpmnProcessId(ProcessConstants.BPMN_PROCESS_ID)
               .latestVersion()
               .variables(variables)
               .send();

    }


}
