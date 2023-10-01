package org.bjing.chat.chat.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
public class ChatFindRequest {

    @Min(0)
    Integer messageLimit;

    @UUID
    String userId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    Date fromDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    Date toDate;
}
