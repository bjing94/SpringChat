package org.bjing.chat.friends.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bjing.chat.common.enums.FriendRequestStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendRequestCreatedResponse {
    Long id;
    FriendRequestStatus status;
}
