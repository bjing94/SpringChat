package org.bjing.chat.chat.mapper;

import org.bjing.chat.chat.dto.MessageCreatedResponse;
import org.bjing.chat.db.entity.Media;
import org.bjing.chat.db.entity.Message;

import java.util.HashSet;
import java.util.stream.Collectors;

public class MessageResponseMapper {
    public static MessageCreatedResponse toResponse(Message message) {
        return MessageCreatedResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .chatId(message.getChat().getId())
                .userId(message.getUser().getId())
                .mediaLinks(message.getMediaFiles().stream().map(Media::getLink).collect(Collectors.toSet()))
                .created(message.getCreated())
                .updated(message.getUpdated()).build();
    }
}
