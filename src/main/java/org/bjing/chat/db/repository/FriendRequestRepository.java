package org.bjing.chat.db.repository;

import org.bjing.chat.db.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    @Query("SELECT fr FROM friend_request fr WHERE fr.sender.id = :senderId AND fr.receiver.id = :receiverId")
    Optional<FriendRequest> findFriendRequestBySenderIdAndReceiverId(@Param("senderId") String senderId,
                                                                     @Param("receiverId") String receiverId);
}
