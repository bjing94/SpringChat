package org.bjing.chat.common;

import lombok.Value;

@Value
public class PaginationResponse<T> {
    T data;
    PaginationMeta meta;
}
