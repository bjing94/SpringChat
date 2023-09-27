package org.bjing.chat.common;

import lombok.Value;

@Value
public class PaginationMeta {
    Integer page;
    Integer pageSize;
    Integer total;
}
