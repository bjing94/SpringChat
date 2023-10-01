package org.bjing.chat.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bjing.chat.common.enums.FriendRequestStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "friend_request")
public class FriendRequest {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne()
    @JoinColumn(name = "user_sender_id", referencedColumnName = "id", nullable = false)
    User sender;

    @ManyToOne()
    @JoinColumn(name = "user_receiver_id", referencedColumnName = "id", nullable = false)
    User receiver;

    @Enumerated(EnumType.STRING)
    FriendRequestStatus status;
}
