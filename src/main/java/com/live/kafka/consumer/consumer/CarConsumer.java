package com.live.kafka.consumer.consumer;

import com.live.kafka.consumer.DTO.CarDTO;
import com.live.kafka.consumer.utils.FileUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CarConsumer {

    private static final Logger log = LoggerFactory.getLogger(CarConsumer.class);

    @Value(value = "${topic.name}")
    private String topic;

    @Value(value = "${spring.kafka.group-id}")
    private String groupId;

    @KafkaListener(topics = "${topic.name}", groupId = "${spring.kafka.group-id}", containerFactory = "carKafkaListenerContainerFactory")
    public void listenTopicCar(ConsumerRecord<String, CarDTO> record) throws IOException {
        log.info("Received Message " + record.partition());
        log.info("Received Message " + record.value());
        FileUtils.createFile();
        FileUtils.writeInFile(record.value().toString(), "src/test/resources/test.txt");
    }


}
