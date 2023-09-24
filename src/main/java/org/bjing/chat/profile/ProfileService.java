package org.bjing.chat.profile;

import lombok.AllArgsConstructor;
import org.bjing.chat.db.entity.User;
import org.bjing.chat.db.repository.UserRepository;
import org.bjing.chat.profile.dto.ProfileResponse;
import org.bjing.chat.profile.dto.ProfileUpdateEmailRequest;
import org.bjing.chat.profile.dto.ProfileUpdateRequest;
import org.bjing.chat.redis.EmailOtp;
import org.bjing.chat.redis.EmailOtpRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;

    private final EmailOtpRepository emailOtpRepository;

    public ProfileResponse getProfile(String userId) {
        Optional<User> optionalUser = this.userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unable to get profile");
        User user = optionalUser.get();

        return ProfileResponse.builder().email(user.getEmail()).firstname(user.getFirstname()).lastname(user.getLastname()).build();
    }

    public ProfileResponse updateProfile(String userId, ProfileUpdateRequest dto) {
        Optional<User> optionalUser = this.userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unable to get profile");
        User user = optionalUser.get();

        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());

        this.userRepository.save(user);

        return ProfileResponse.builder().email(user.getEmail()).firstname(user.getFirstname()).lastname(user.getLastname()).build();
    }

    public ProfileResponse updateEmail(String userId, ProfileUpdateEmailRequest dto) {
        Optional<User> optionalUser = this.userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unable to get profile");
        User user = optionalUser.get();

//       TODO: Set some key to redis etc
        this.emailOtpRepository.save(new EmailOtp(user.getEmail(), "122-122", dto.getEmail()));

        return ProfileResponse.builder().email(user.getEmail()).firstname(user.getFirstname()).lastname(user.getLastname()).build();
    }

    public ProfileResponse confirmEmailUpdate(String userId, String code) {
        Optional<User> optionalUser = this.userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unable to get profile");
        User user = optionalUser.get();

        EmailOtp emailOtp = this.emailOtpRepository
                .findById(user.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request to change email not found!"));

        if (!emailOtp.getCode().equals(code))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Code is not valid");

        user.setEmail(emailOtp.getNewEmail());
        this.userRepository.save(user);

        return ProfileResponse.builder().email(user.getEmail()).firstname(user.getFirstname()).lastname(user.getLastname()).build();
    }
}
