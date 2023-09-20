package org.bjing.chat.chat.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bjing.chat.db.entity.Chat;
import org.bjing.chat.db.entity.Media;
import org.bjing.chat.db.entity.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageCreatedResponse {
    Long id;
    String content;
    String userId;
    String chatId;
    Set<String> mediaLinks;
    Date created;
    Date updated;
}
