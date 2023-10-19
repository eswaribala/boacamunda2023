package com.boa.kafkademo;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public final class KafkaConsumerDemo {
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
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());
        properties.setProperty("group.id", "groupid");

        // create a consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        consumer.subscribe(Arrays.asList(TOPIC_NAME));
        while (true) {
            ConsumerRecords<String, String> messages = consumer.poll(Duration.ofMillis(100));
            messages.forEach(message -> {
              System.out.println("Got message using SSL: " + message.value());
            });
        }
    }
}
