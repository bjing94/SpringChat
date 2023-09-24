package org.bjing.chat.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("EmailOtp")
@AllArgsConstructor
@Data
public class EmailOtp implements Serializable {
    private String id;
    private String code;
    private String newEmail;
}
