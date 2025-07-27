package com.sam.publish_ride_service.service;


import com.sam.publish_ride_service.dto.RideResponse;
import com.sam.publish_ride_service.util.KafkaTopicChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer {

    private KafkaTemplate<String, RideResponse> kafkaTemplate;

    private KafkaTopicChecker topicChecker;

    public KafkaProducer(KafkaTemplate<String, RideResponse> kafkaTemplate, KafkaTopicChecker topicChecker) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicChecker = topicChecker;
    }


    public void sendRide(RideResponse ride, String topic) {
        if(topicChecker.topicExists(topic)) {
            System.out.println("Topic exists, sending ride: " + ride);
            log.info("Topic exists, sending ride: {}", ride);
        } else {
            System.out.println("Topic does not exist, cannot send ride: " + ride);
            log.info("Topic does not exist, cannot send ride: {}", ride);
            return;
        }
        kafkaTemplate.send(topic, ride);
    }
}