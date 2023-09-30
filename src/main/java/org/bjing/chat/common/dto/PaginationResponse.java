package org.bjing.chat.common.dto;

import lombok.Value;

@Value
public class PaginationResponse<T> {
    T data;
    PaginationMeta meta;
}
