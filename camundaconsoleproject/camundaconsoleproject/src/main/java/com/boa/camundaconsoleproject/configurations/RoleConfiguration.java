package com.boa.camundaconsoleproject.configurations;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class RoleConfiguration {
	@JobWorker(type = "assign_activity",autoComplete = false)
	public Map<String,Object> assignActivity(final JobClient jobClient, final ActivatedJob activatedJob) {
		
	
		
	    Map<String,Object> activityMap=new HashMap<String,Object>();
	    
		 activityMap.put("activity", "codedevelopment");
		
        jobClient.newCompleteCommand(activatedJob.getKey())
               .variables(activityMap)
                .send()
                .exceptionally(throwable -> {
                    throw new RuntimeException("Could not complete job " + jobClient, throwable);
                });
       
	    	
        log.info("Role job done...");		
		return activityMap;
		
	}
	
	@JobWorker(type = "assign_roles",autoComplete = false)
	public Map<String,Object> assignRoles(final JobClient jobClient, final ActivatedJob activatedJob) throws JsonMappingException, JsonProcessingException {
		
	    Map<String,Object> rolesMap=activatedJob.getVariablesAsMap();
	    
	    String rolesData=rolesMap.get("roles").toString();
	    Map<String,Object> rolesValue=new HashMap<>();
	    
		if(rolesData!=null) {
			rolesValue.put("assignee","vhebcompany@gmail.com");
			
		  	
        jobClient.newCompleteCommand(activatedJob.getKey())
             .variables(rolesValue)
                .send()
                .exceptionally(throwable -> {
                    throw new RuntimeException("Could not complete job " + jobClient, throwable);
                });
		}
		else
		{
			
				rolesValue.put("assignee","");
				
			  	
	        jobClient.newCompleteCommand(activatedJob.getKey())
	             .variables(rolesValue)
	                .send()
	                .exceptionally(throwable -> {
	                    throw new RuntimeException("Could not complete job " + jobClient, throwable);
	                });
			
		}
		
	    
        log.info("Role job done...");		
		return rolesValue;
		
	}
}
