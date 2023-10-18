package com.boa.camundaconsoleproject;

import java.time.Duration;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.camunda.zeebe.client.ZeebeClient;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class CamundaconsoleprojectApplication  {
//implements CommandLineRunner{
    @Autowired
	private ZeebeClient zeebeClient; 
	public static void main(String[] args) {
		SpringApplication.run(CamundaconsoleprojectApplication.class, args);
	}

	/*
	 * @Override public void run(String... args) throws Exception { // TODO
	 * Auto-generated method stub
	 * zeebeClient.newPublishMessageCommand().messageName(
	 * "message_trigger_api_call") .correlationKey(String.valueOf(new
	 * Random().nextInt(10000))) .timeToLive(Duration.ofMinutes(2)) .send()
	 * 
	 * .exceptionally(throwable -> { throw new
	 * RuntimeException("Could not complete job " + zeebeClient, throwable); });
	 * log.info("Message published"); }
	 */

}
