package org.bjing.chat.chat.mapper;

import org.bjing.chat.chat.dto.ChatResponse;
import org.bjing.chat.db.entity.Chat;
import org.bjing.chat.db.entity.User;

import java.util.stream.Collectors;

public class ChatResponseMapper {
    public static ChatResponse map(Chat chat) {
        return ChatResponse.builder()
                .id(chat.getId())
                .creatorId(chat.getCreator().getId())
                .userIds(chat.getUsers().stream().map(User::getId).collect(Collectors.toSet()))
                .mediaFiles(chat.getMediaFiles())
                .created(chat.getCreated())
                .updated(chat.getUpdated())
                .build();
    }
}
