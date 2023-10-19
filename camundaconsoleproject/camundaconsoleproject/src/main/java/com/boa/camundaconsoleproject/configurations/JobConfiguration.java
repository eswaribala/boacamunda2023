package com.boa.camundaconsoleproject.configurations;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;


@Configuration
@Slf4j
public class JobConfiguration {
	@Autowired
	private ZeebeClient zeebeClient;

	@JobWorker(type = "fetch-users",autoComplete = false)
	public Map<String,Object> fetchUserData(final JobClient jobClient, final ActivatedJob activatedJob) {
		
		Map<String,Object> map=activatedJob.getVariablesAsMap();
		
	    System.out.println(map.get("users"));
	    System.out.println("Map Size"+map.size());
		
	    Map<String,Object> checkMap=new HashMap<String,Object>();
	    if(map.containsKey("users")) {
		checkMap.put("status", true);
		checkMap.put("counter", map.size());
		 zeebeClient.newPublishMessageCommand().messageName("message_event_subprocess")
		    .correlationKey("100")
		    .timeToLive(Duration.ofMinutes(30))
		    .send()

		    .exceptionally(throwable -> {
		        throw new RuntimeException("Could not complete job " + zeebeClient, throwable);
		    });
		    log.info("Message published");

		
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
	    	 zeebeClient.newPublishMessageCommand().messageName("message_event_subprocess")
			    .correlationKey("100")
			    .timeToLive(Duration.ofMinutes(30))
			    .send()

			    .exceptionally(throwable -> {
			        throw new RuntimeException("Could not complete job " + zeebeClient, throwable);
			    });
			    log.info("Message published");
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
	
	@JobWorker(type = "verify_user_count",autoComplete = false)
	public Map<String,Object> verifyUserCount(final JobClient jobClient, final ActivatedJob activatedJob) {
		
		Map<String,Object> map=activatedJob.getVariablesAsMap();
		
		//int count=Integer.parseInt(map.get("counter").toString());
		
	    //System.out.println(count);
		
	    Map<String,Object> countMap=new HashMap<String,Object>();
	    if(map.size()>1) {
		 log.info("Mail Triggered");
		 countMap.put("count", 1);
        jobClient.newCompleteCommand(activatedJob.getKey())
               .variables(countMap)
                .send()
                .exceptionally(throwable -> {
                    throw new RuntimeException("Could not complete job " + jobClient, throwable);
                });
        
	    }
	    else
	    {
	    	countMap.put("count", 0);
	        jobClient.newCompleteCommand(activatedJob.getKey())
	               .variables(countMap)
	                .send()
	                .exceptionally(throwable -> {
	                    throw new RuntimeException("Could not complete job " + jobClient, throwable);
	                });
	    }
	    	
        log.info("job done...");		
		return countMap;
		
	}

	
}
