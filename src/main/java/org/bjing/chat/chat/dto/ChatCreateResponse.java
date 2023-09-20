package org.bjing.chat.chat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ChatCreateResponse {
    List<String> userIds;
    String creatorId;
    String id;
}
