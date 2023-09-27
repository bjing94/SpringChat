package org.bjing.chat.user;

import lombok.AllArgsConstructor;
import org.bjing.chat.common.PaginationRequest;
import org.bjing.chat.common.PaginationResponse;
import org.bjing.chat.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.userService.getUser(id));
    }

    @GetMapping()
    public ResponseEntity<PaginationResponse<Set<UserResponse>>> findUsers(PaginationRequest pagination) {
        return ResponseEntity.ok(this.userService.findUsers(pagination));
    }
}
