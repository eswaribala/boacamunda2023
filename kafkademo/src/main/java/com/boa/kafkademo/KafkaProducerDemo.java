package com.boa.kafkademo;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public final class KafkaProducerDemo {
    public static void main(String[] args) {
        String TOPIC_NAME = "EMITopic";
        String TRUSTSTORE_PASSWORD = "vignesh";
        String KEYSTORE_PASSWORD = "vignesh";
        String KEY_PASSWORD = "vignesh";

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "kafka-39de25fb-parameswaribala-4c17.a.aivencloud.com:16850");
        properties.setProperty("security.protocol", "SSL");
        properties.setProperty("ssl.truststore.location", "client.truststore.jks");
        properties.setProperty("ssl.truststore.password", TRUSTSTORE_PASSWORD);
        properties.setProperty("ssl.keystore.type", "PKCS12");
        properties.setProperty("ssl.keystore.location", "client.keystore.p12");
        properties.setProperty("ssl.keystore.password", KEYSTORE_PASSWORD);
        properties.setProperty("ssl.key.password", KEY_PASSWORD);
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        // create a producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // produce 100 messages
        for (int i = 0; i < 100; i++) {
            String message = "Hello from Java using SSL " + (i + 1) + "!";
            producer.send(new ProducerRecord<String, String>(TOPIC_NAME, message));
            System.out.println("Message sent: " + message);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }

        producer.close();
    }
}