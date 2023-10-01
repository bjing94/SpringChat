package org.bjing.chat.profile;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.bjing.chat.auth.AuthResponse;
import org.bjing.chat.chat.dto.ChatResponse;
import org.bjing.chat.db.entity.Chat;
import org.bjing.chat.db.entity.User;
import org.bjing.chat.profile.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/profile")
@AllArgsConstructor
public class ProfileController {
    private final ProfileService profileService;


    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(this.profileService.getProfile(user.getId()));
    }

    @PutMapping
    public ResponseEntity<ProfileResponse> updateProfile(@RequestBody ProfileUpdateRequest dto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(this.profileService.updateProfile(user.getId(), dto));
    }

    @PatchMapping("/avatar")
    public ResponseEntity<ProfileResponse> updateProfile(Authentication authentication,
                                                         @RequestParam("file") MultipartFile file) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(this.profileService.updateAvatar(user.getId(), file));
    }


    @PatchMapping("/email")
    public ResponseEntity<ProfileResponse> updateProfileEmail(@RequestBody ProfileUpdateEmailRequest dto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(this.profileService.updateEmail(user.getId(), dto));
    }

    @PatchMapping("/email/confirm")
    public ResponseEntity<AuthResponse> updateProfileEmailConfirm(@RequestBody ProfileConfirmEmailUpdateRequest dto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(this.profileService.confirmEmailUpdate(user.getId(), dto));
    }

    @GetMapping("chats")
    public ResponseEntity<List<ChatResponse>> getUserChats(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(this.profileService.getUserChats(user.getId()));
    }
}
