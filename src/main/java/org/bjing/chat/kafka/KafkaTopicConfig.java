package org.bjing.chat.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

// Create topics
@Configuration
public class KafkaTopicConfig {
    @Value("${kafka.topic.chat-created}")
    private String chatCreatedTopic;

    @Value("${kafka.topic.message-sent}")
    private String messageSentTopic;

    @Bean
    public NewTopic createChatCreatedTopic(){
        return TopicBuilder.name(this.chatCreatedTopic)
                .build();
    }

    @Bean
    public NewTopic createMessageSentTopic(){
        return TopicBuilder.name(this.messageSentTopic)
                .build();
    }
}
