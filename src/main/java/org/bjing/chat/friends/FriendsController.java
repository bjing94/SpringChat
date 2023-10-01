package org.bjing.chat.friends;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bjing.chat.db.entity.User;
import org.bjing.chat.friends.dto.FriendRequestCreateRequest;
import org.bjing.chat.friends.dto.FriendRequestCreatedResponse;
import org.bjing.chat.friends.dto.FriendRequestStatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendsController {

    private final FriendsService friendsService;

    @PostMapping("/request")
    public ResponseEntity<FriendRequestCreatedResponse> sendFriendRequest(@RequestBody @Valid FriendRequestCreateRequest data, Authentication authentication

    ) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(this.friendsService.sendFriendRequest(user.getId(), data.getUserId()));
    }

    @PostMapping("/request/accept")
    public ResponseEntity<FriendRequestStatusResponse> acceptFriendRequest(@RequestBody @Valid FriendRequestCreateRequest data, Authentication authentication

    ) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(this.friendsService.acceptFriendRequest(user.getId(), data.getUserId()));
    }

    @PostMapping("/request/decline")
    public ResponseEntity<FriendRequestStatusResponse> declineFriendRequest(@RequestBody @Valid FriendRequestCreateRequest data, Authentication authentication

    ) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(this.friendsService.declineFriendRequest(user.getId(), data.getUserId()));
    }
}
