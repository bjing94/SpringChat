package org.bjing.chat.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.bjing.chat.chat.dto.ChatCreateResponse;
import org.bjing.chat.chat.dto.MessageCreatedResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> stringKafkaTemplate;
    private final KafkaTemplate<String, Object> objectKafkaTemplate;

    @Value("${kafka.topic.chat-created}")
    private String chatCreatedTopic;

    @Value("${kafka.topic.message-sent}")
    private String messageSentTopic;

    public void sendChatCreatedMessage(ChatCreateResponse data) {
        this.objectKafkaTemplate.send(this.chatCreatedTopic, data);
    }

    public void sendMessageSentMessage(MessageCreatedResponse data) {
        this.objectKafkaTemplate.send(this.messageSentTopic, data);
    }
}
