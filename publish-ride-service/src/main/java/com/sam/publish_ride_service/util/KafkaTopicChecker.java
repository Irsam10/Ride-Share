package com.sam.publish_ride_service.util;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ExecutionException;

@Component
public class KafkaTopicChecker {

    private final KafkaAdmin kafkaAdmin;

    @Autowired
    public KafkaTopicChecker(KafkaAdmin kafkaAdmin) {
        this.kafkaAdmin = kafkaAdmin;
    }

    public boolean topicExists(String topicName) {
        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            ListTopicsResult topics = adminClient.listTopics();
            Set<String> names = topics.names().get();
            return names.contains(topicName);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to check topic existence", e);
        }
    }
}