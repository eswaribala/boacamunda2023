package com.boa.camundaconsoleproject.configurations;

import java.util.Iterator;
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
	public void fetchUserData(final JobClient jobClient, final ActivatedJob activatedJob) {
		
		Map<String,Object> map=activatedJob.getVariablesAsMap();
		
		Iterator<Map.Entry<String,Object>> itr=map.entrySet().iterator();
		
		Map.Entry<String,Object> entry=null;
		while(itr.hasNext()) {
			entry=itr.next();
			log.info(entry.getKey(),entry.getValue());			
		}		

        jobClient.newCompleteCommand(activatedJob.getKey())
               // .variables(map)
                .send()
                .exceptionally(throwable -> {
                    throw new RuntimeException("Could not complete job " + jobClient, throwable);
                });
        log.info("job done...");

		
		
		
	}
	
	
}
