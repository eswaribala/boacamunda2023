package com.boa.loanapp.configurations;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Configuration
@Slf4j
public class JobConfiguration {

    private  Map<String, Object> map;
    //Generate the variable
    @JobWorker(type = "random_number_task",autoComplete = false)
    public Map<String,Object> handleRandomNumberGenerator(final JobClient jobClient, final ActivatedJob activatedJob){

        map=new HashMap<String,Object>();
        map.put("loanApplicationNo", new Random().nextInt(1000000));

        jobClient.newCompleteCommand(activatedJob.getKey())
                .variables(map)
                .send()
                .exceptionally(throwable -> {
                    throw new RuntimeException("Could not complete job " + jobClient, throwable);
                });
        log.info("job done...");
        return map;
    }

//fetch the variable
    @JobWorker(type = "show_generated_application_no", autoComplete = false)
    public void showGeneratedApplicationNo(final JobClient jobClient, final  ActivatedJob activatedJob){
          map=    activatedJob.getVariablesAsMap();

          //log.info("Show the Generated Application No", map.get("loanApplicationNo"));
     System.out.println("Show the Generated Application No "+map.get("loanApplicationNo"));
        jobClient.newCompleteCommand(activatedJob.getKey())
               // .variables(map)
                .send()
                .exceptionally(throwable -> {
                    throw new RuntimeException("Could not complete job " + jobClient, throwable);
                });
        log.info("job done...");

    }


    @JobWorker(type = "check_age", autoComplete = false)
    public Map<String,Object> validateDOBandReturnAge(final JobClient jobClient, final  ActivatedJob activatedJob){
            map=    activatedJob.getVariablesAsMap();

        //log.info("Show the Generated Application No", map.get("loanApplicationNo"));
        System.out.println("Show the Generated Application No "+map.get("loanApplicationNo"));
        LocalDate dob= LocalDate.parse (map.get("dob").toString());
        HashMap<String, Object> ageMap=new HashMap<String,Object>();
        long age=0;
        if(dob.isBefore(LocalDate.now())) {

            age=ChronoUnit.YEARS.between(dob,LocalDate.now());
            ageMap.put("age",age);
            ageMap.put("checkdob",true);
            jobClient.newCompleteCommand(activatedJob.getKey())
                     .variables(ageMap)
                    .send()
                    .exceptionally(throwable -> {
                        throw new RuntimeException("Could not complete job " + jobClient, throwable);
                    });
            log.info("job done...");
        }
        else
        {
            ageMap.put("checkdob",false);
            jobClient.newCompleteCommand(activatedJob.getKey())
                    .variables(ageMap)
                    .send()
                    .exceptionally(throwable -> {
                        throw new RuntimeException("Could not complete job " + jobClient, throwable);
                    });
            log.info("job done...");
        }
        return ageMap;
    }

}
