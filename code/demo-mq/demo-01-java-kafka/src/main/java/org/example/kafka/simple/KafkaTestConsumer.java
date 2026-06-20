package org.example.kafka.simple;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaTestConsumer {
    public static String getGroupId() {
        return "spring-console-";
    }

    @KafkaListener(
            topics = "kafka-test",
            groupId = "#{T(org.example.kafka.simple.KafkaTestConsumer).getGroupId()}",
            properties = {
                    "auto.offset.reset=earliest"
            }
    )
    public void echo(ConsumerRecord<String, String> record) {
        log.info("[kafka-test] {}", record.value());
    }
}
