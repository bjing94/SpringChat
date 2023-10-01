package org.bjing.chat.common.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class PaginationRequest {

    @NotNull
    @Min(1)
    Integer pageSize;

    @NotNull
    @Min(0)
    Integer page;
}
