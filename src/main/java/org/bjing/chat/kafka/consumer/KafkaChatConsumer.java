package org.bjing.chat.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaChatConsumer {

    @KafkaListener(topics = "${kafka.topic.chat-created}",
            groupId = "bar")
    void onChatCreated(String data) {
        System.out.println("Received: " + data);
    }

    @KafkaListener(topics = "${kafka.topic.message-sent}",
            groupId = "bar")
    void onMessageSent(String data) {
        System.out.println("Received: " + data);
    }

}

