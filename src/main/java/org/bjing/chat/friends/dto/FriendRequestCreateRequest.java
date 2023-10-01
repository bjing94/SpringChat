package org.bjing.chat.friends.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FriendRequestCreateRequest {

    @NotEmpty
    String userId;
}
