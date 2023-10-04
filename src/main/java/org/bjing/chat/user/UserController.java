package org.bjing.chat.user;

import lombok.AllArgsConstructor;
import org.bjing.chat.common.dto.PaginationRequest;
import org.bjing.chat.common.dto.PaginationResponse;
import org.bjing.chat.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.userService.getUser(id));
    }

    @GetMapping()
    public ResponseEntity<PaginationResponse<Set<UserResponse>>> findUsers(PaginationRequest pagination) {
        return ResponseEntity.ok(this.userService.findUsers(pagination));
    }
}
