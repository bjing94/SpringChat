package org.bjing.chat.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class MessageCreateDto {
    String userId;
    String chatId;
    String content;
    MultipartFile file;
}
