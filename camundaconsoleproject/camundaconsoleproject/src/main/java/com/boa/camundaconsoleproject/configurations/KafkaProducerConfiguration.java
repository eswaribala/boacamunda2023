package com.boa.camundaconsoleproject.configurations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;

@Configuration
public class KafkaProducerConfiguration {
    @Value("${TOPIC_NAME}")
	private  String topicName;
    @Value("${password}")
    private String truststorePassword; 
    @Value("${password}")
    private String keyStorePassword;
    @Value("${password}")
    private String keyPassword;
    @Value("${bootstrapserver}")
    private String bootstrapServer;
    @Value("${securityprotocol}")
    private String securityProtocol;
    @Value("${truststorelocation}")
    private String sslTruststoreLocation;
    @Value("${keystorelocation}")
    private String keyStoreLocation;
    @Value("${keystoretype}")
    private String keyStoreType;
  
    @JobWorker(type = "",autoComplete = false)
    public Map<String,Object> triggerMessagePublishing(final JobClient jobClient, final ActivatedJob activatedJob){
    	
    	
    	Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", bootstrapServer);
        properties.setProperty("security.protocol", securityProtocol);
        properties.setProperty("ssl.truststore.location", sslTruststoreLocation);
        properties.setProperty("ssl.truststore.password", truststorePassword);
        properties.setProperty("ssl.keystore.type", keyStoreType);
        properties.setProperty("ssl.keystore.location", keyStoreLocation);
        properties.setProperty("ssl.keystore.password", keyStorePassword);
        properties.setProperty("ssl.key.password", keyPassword);
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        
     // create a producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        List<ProducerRecord<String,String>>  messages=new ArrayList<>(); 
        ProducerRecord<String,String> producerRecord=null;
        Map<String,Object> map=new HashMap<String,Object>();
        // produce 100 messages
        for (int i = 0; i < 5; i++) {
            String message = "Message from Camunda " + (i + 1) + "!";
            producerRecord=new ProducerRecord<String, String>(topicName, message);
            producer.send(producerRecord);
            messages.add(producerRecord);
            System.out.println("Message sent: " + message);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }
        producer.close();
        map.put("camundaMessages", messages);
        jobClient.newCompleteCommand(activatedJob.getKey())
        .variables(map)
         .send()
         .exceptionally(throwable -> {
             throw new RuntimeException("Could not complete job " + jobClient, throwable);
         });
 
        return map;
    	
    }
    
    
    
}
