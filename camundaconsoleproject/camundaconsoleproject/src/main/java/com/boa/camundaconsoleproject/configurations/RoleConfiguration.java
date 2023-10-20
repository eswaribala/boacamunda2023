package com.boa.camundaconsoleproject.configurations;

import java.lang.reflect.Type;
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
	public void assignRoles(final JobClient jobClient, final ActivatedJob activatedJob) throws JsonMappingException, JsonProcessingException {
		
	    Map<String,Object> rolesMap=activatedJob.getVariablesAsMap();
	    String values=rolesMap.get("roles").toString();
	System.out.println(values);
	    Gson gson = new Gson();
	    Type listType = new TypeToken<List<RoleMapper>>() {
        }.getType();
	    List<RoleMapper> roleMappers= new Gson().fromJson(values, listType);
		System.out.println(roleMappers);
	    	//assigneeMap.put("assignee", assignee);
	    	//assigneeMap.put("candidateusers", "");
        jobClient.newCompleteCommand(activatedJob.getKey())
             //  .variables(assigneeMap)
                .send()
                .exceptionally(throwable -> {
                    throw new RuntimeException("Could not complete job " + jobClient, throwable);
                });
       
	    
        log.info("Role job done...");		
		//return assigneeMap;
		
	}
}
