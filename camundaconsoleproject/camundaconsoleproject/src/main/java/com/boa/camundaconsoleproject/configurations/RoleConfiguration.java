package com.boa.camundaconsoleproject.configurations;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Configuration;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class RoleConfiguration {
	@JobWorker(type = "assign_activity",autoComplete = false)
	public Map<String,Object> verifyUserCount(final JobClient jobClient, final ActivatedJob activatedJob) {
		
	
		
	    Map<String,Object> activityMap=new HashMap<String,Object>();
	    
		 activityMap.put("activity", "codedevelopment");
		 activityMap.put("assignee", "");
		 activityMap.put("candidateusers", "");
        jobClient.newCompleteCommand(activatedJob.getKey())
               .variables(activityMap)
                .send()
                .exceptionally(throwable -> {
                    throw new RuntimeException("Could not complete job " + jobClient, throwable);
                });
       
	    	
        log.info("Role job done...");		
		return activityMap;
		
	}
}
