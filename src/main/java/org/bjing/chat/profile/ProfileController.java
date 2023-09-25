package org.bjing.chat.profile;

import lombok.AllArgsConstructor;
import org.bjing.chat.auth.AuthResponse;
import org.bjing.chat.db.entity.User;
import org.bjing.chat.profile.dto.ProfileConfirmEmailUpdateRequest;
import org.bjing.chat.profile.dto.ProfileResponse;
import org.bjing.chat.profile.dto.ProfileUpdateEmailRequest;
import org.bjing.chat.profile.dto.ProfileUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
}
