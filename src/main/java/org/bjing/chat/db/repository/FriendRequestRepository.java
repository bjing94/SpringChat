package org.bjing.chat.db.repository;

import org.bjing.chat.db.entity.FriendRequest;
import org.bjing.chat.db.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    @Query("SELECT fr FROM friend_request fr WHERE fr.sender.id = :senderId AND fr.receiver.id = :receiverId")
    Optional<FriendRequest> findFriendRequestBySenderIdAndReceiverId(@Param("senderId") String senderId,
                                                                     @Param("receiverId") String receiverId);

    @Query("SELECT u FROM friend_request fr LEFT JOIN users u ON fr.sender = u" +
            " WHERE u.id = :userId AND fr.status = 'ACCEPTED' ")
    Page<User> findFriendsSender(@Param("userId") String userId, Pageable pageable);

    @Query("SELECT u FROM friend_request fr LEFT JOIN users u ON fr.receiver = u" +
            " WHERE u.id = :userId AND fr.status = 'ACCEPTED' ")
    Page<User> findFriendsReceiver(@Param("userId") String userId, Pageable pageable);
}
