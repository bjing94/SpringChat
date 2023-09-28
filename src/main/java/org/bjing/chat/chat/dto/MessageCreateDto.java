package org.bjing.chat.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Data
@AllArgsConstructor
public class MessageCreateDto {
    String userId;
    String chatId;
    String content;
    Optional<MultipartFile> file;
}
