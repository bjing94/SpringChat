package org.bjing.chat.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bjing.chat.db.entity.Media;
import org.bjing.chat.db.entity.Message;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {
    private String id;

    private Set<String> userIds;

    private String creatorId;

    private Set<Media> mediaFiles;

    private Set<MessageCreatedResponse> messages;

    private Date created;


    private Date updated;
}
