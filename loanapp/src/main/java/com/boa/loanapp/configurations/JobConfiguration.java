package com.boa.loanapp.configurations;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Configuration
@Slf4j
public class JobConfiguration {
    @JobWorker(type = "random_number_task")
    public Map<String,Object> handleRandomNumberGenerator(final JobClient jobClient, final ActivatedJob activatedJob){

        Map<String, Object> map=new HashMap<String,Object>();
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

}
