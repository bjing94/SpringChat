package org.bjing.chat.db.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
public class ChatFindFilter {
    Integer messageLimit;
    String userId;
    Date fromDate;
    Date toDate;
}
