package com.boa.camundaconsoleproject.configurations;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class KafkaConsumerConfiguration {
	
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
	    @Value("${TOPIC_NAME}")
	    private final String kafkaTopicName=""; 
	  
	    
	    public void triggerMessageConsumption(){
	    	
	    
	    	Properties properties = new Properties();
	        properties.setProperty("bootstrap.servers", bootstrapServer);
	        properties.setProperty("security.protocol", securityProtocol);
	        properties.setProperty("ssl.truststore.location", sslTruststoreLocation);
	        properties.setProperty("ssl.truststore.password", truststorePassword);
	        properties.setProperty("ssl.keystore.type", keyStoreType);
	        properties.setProperty("ssl.keystore.location", keyStoreLocation);
	        properties.setProperty("ssl.keystore.password", keyStorePassword);
	        properties.setProperty("ssl.key.password", keyPassword);
	        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
	        properties.setProperty("value.deserializer", StringDeserializer.class.getName());
	        properties.setProperty("group.id", "groupid");
	     // create a consumer
	        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

	        consumer.subscribe(Arrays.asList(topicName));
	        while (true) {
	            ConsumerRecords<String, String> messages = consumer.poll(Duration.ofMillis(100));
	            messages.forEach(message -> {
	              System.out.println("Got message using SSL: " + message.value());
	            });
	        }
	    	
	    }
	    
}
