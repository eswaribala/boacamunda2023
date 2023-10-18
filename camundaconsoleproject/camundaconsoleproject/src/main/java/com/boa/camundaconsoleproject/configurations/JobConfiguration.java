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
public class JobConfiguration {
	

	@JobWorker(type = "fetch-users",autoComplete = false)
	public Map<String,Object> fetchUserData(final JobClient jobClient, final ActivatedJob activatedJob) {
		
		Map<String,Object> map=activatedJob.getVariablesAsMap();
		
	    System.out.println(map.get("users"));
		
	    Map<String,Object> checkMap=new HashMap<String,Object>();
	    if(map.containsKey("users")) {
		checkMap.put("status", true);
        jobClient.newCompleteCommand(activatedJob.getKey())
               .variables(checkMap)
                .send()
                .exceptionally(throwable -> {
                    throw new RuntimeException("Could not complete job " + jobClient, throwable);
                });
        
	    }
	    else
	    {
	    	checkMap.put("status", false);
	        jobClient.newCompleteCommand(activatedJob.getKey())
	               .variables(checkMap)
	                .send()
	                .exceptionally(throwable -> {
	                    throw new RuntimeException("Could not complete job " + jobClient, throwable);
	                });
	    }
	    	
        log.info("job done...");		
		return checkMap;
		
	}
	
	

	
}
