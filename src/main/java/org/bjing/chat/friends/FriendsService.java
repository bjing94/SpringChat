package org.bjing.chat.friends;

import lombok.RequiredArgsConstructor;
import org.bjing.chat.common.enums.FriendRequestStatus;
import org.bjing.chat.db.entity.FriendRequest;
import org.bjing.chat.db.entity.User;
import org.bjing.chat.db.repository.FriendRequestRepository;
import org.bjing.chat.db.repository.UserRepository;
import org.bjing.chat.friends.dto.FriendRequestCreatedResponse;
import org.bjing.chat.friends.dto.FriendRequestStatusResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendsService {

    private final FriendRequestRepository friendRequestRepository;

    private final UserRepository userRepository;

    public FriendRequestCreatedResponse sendFriendRequest(String userId, String receiverId) {
        if (userId.equals(receiverId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cant add yourself to friend list");

        User sender = this.userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        User receiver = this.userRepository.findById(receiverId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Optional<FriendRequest> existingRequest = this.friendRequestRepository.findFriendRequestBySenderIdAndReceiverId(userId, receiverId);
        if (existingRequest.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Friend request already exists");
        }

        FriendRequest friendRequest = FriendRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .status(FriendRequestStatus.PENDING)
                .build();

        FriendRequest savedFriendRequest = this.friendRequestRepository.save(friendRequest);
        return FriendRequestCreatedResponse.builder()
                .id(savedFriendRequest.getId())
                .status(savedFriendRequest.getStatus())
                .build();
    }

    public FriendRequestStatusResponse acceptFriendRequest(String userId, String senderId) {
        FriendRequest friendRequest = this.friendRequestRepository
                .findFriendRequestBySenderIdAndReceiverId(senderId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (friendRequest.getStatus() == FriendRequestStatus.ACCEPTED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        friendRequest.setStatus(FriendRequestStatus.ACCEPTED);

        this.friendRequestRepository.save(friendRequest);

        return FriendRequestStatusResponse.builder()
                .status(friendRequest.getStatus())
                .id(friendRequest.getId()).build();
    }

    public FriendRequestStatusResponse declineFriendRequest(String userId, String senderId) {
        FriendRequest friendRequest = this.friendRequestRepository
                .findFriendRequestBySenderIdAndReceiverId(senderId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (friendRequest.getStatus() == FriendRequestStatus.DECLINED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        friendRequest.setStatus(FriendRequestStatus.DECLINED);

        this.friendRequestRepository.save(friendRequest);

        return FriendRequestStatusResponse.builder()
                .status(friendRequest.getStatus())
                .id(friendRequest.getId()).build();

    }
}
