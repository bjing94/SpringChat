package org.bjing.chat.common.dto;

import lombok.Value;

@Value
public class PaginationMeta {
    Integer page;
    Integer pageSize;
    Integer total;
}
