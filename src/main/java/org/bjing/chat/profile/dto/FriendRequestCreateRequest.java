package org.bjing.chat.profile.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FriendRequestCreateRequest {

    @NotEmpty
    String userId;
}
