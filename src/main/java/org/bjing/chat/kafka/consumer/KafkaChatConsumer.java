package org.bjing.chat.kafka.consumer;

import org.bjing.chat.chat.dto.ChatCreateResponse;
import org.bjing.chat.chat.dto.MessageCreatedResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaChatConsumer {

    @KafkaListener(topics = "${kafka.topic.chat-created}",
            groupId = "bar")
    void onChatCreated(ChatCreateResponse data) {
        System.out.println("Received: " + data);
    }

    @KafkaListener(topics = "${kafka.topic.message-sent}",
            groupId = "bar")
    void onMessageSent(MessageCreatedResponse data) {
        System.out.println("Received: " + data);
    }

}

