package org.bjing.chat.file;

import lombok.Value;

@Value
public class FileCreatedResponse {
    String link;
    String filename;
    Long size;
}
