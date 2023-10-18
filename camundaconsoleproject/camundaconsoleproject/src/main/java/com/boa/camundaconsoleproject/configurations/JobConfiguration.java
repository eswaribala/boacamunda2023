package com.boa.camundaconsoleproject.configurations;

import java.util.Iterator;
import java.util.Map;

import org.springframework.context.annotation.Configuration;

import com.github.wnameless.json.flattener.JsonFlattener;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

@Configuration
@Slf4j
public class JobConfiguration {

	@JobWorker(type = "fetch-users",autoComplete = false)
	public void fetchUserData(final JobClient jobClient, final ActivatedJob activatedJob) {
		
		Map<String,Object> map=activatedJob.getVariablesAsMap();
		
		Iterator<Map.Entry<String,Object>> itr=map.entrySet().iterator();
		
		Map.Entry<String,Object> entry=null;
		Map<String,Object> result=null;
		while(itr.hasNext()) {
			entry=itr.next();
			System.out.println(entry.getKey()+","+entry.getValue());
			result=parseString(entry.getValue().toString());
		}		

		itr=result.entrySet().iterator();
		entry=null;	
		while(itr.hasNext()) {
			entry=itr.next();
			System.out.println(entry.getKey()+","+entry.getValue());
			
		}		
		
		
        jobClient.newCompleteCommand(activatedJob.getKey())
               // .variables(map)
                .send()
                .exceptionally(throwable -> {
                    throw new RuntimeException("Could not complete job " + jobClient, throwable);
                });
        log.info("job done...");

		
		
		
	}
	
	
	//response string to object and separates the token

    private static Map<String, Object> parseString(String response)
    {
        JSONParser parser = new JSONParser();
        Map<String, Object> flattenedJsonMap=null;
        String token="";
        try {

            // Put above JSON content to crunchify.txt file and change path location
            Object obj = parser.parse(response);
            JSONObject jsonObject = (JSONObject) obj;

            // JsonFlattener: A Java utility used to FLATTEN nested JSON objects
            String flattenedJson = JsonFlattener.flatten(jsonObject.toString());
            //log("\n=====Simple Flatten===== \n" + flattenedJson);

            flattenedJsonMap= JsonFlattener.flattenAsMap(jsonObject.toString());
              token=(String) flattenedJsonMap.get("body");
            //log("\n=====Flatten As Map=====\n" + flattenedJson);
            // We are using Java8 forEach loop. More info: https://crunchify.com/?p=8047
            //flattenedJsonMap.forEach((k, v) -> log(k + " : " + v));

            // Unflatten it back to original JSON
           // String nestedJson = JsonUnflattener.unflatten(flattenedJson);
           // System.out.println("\n=====Unflatten it back to original JSON===== \n" + nestedJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flattenedJsonMap;

    }

	
}
