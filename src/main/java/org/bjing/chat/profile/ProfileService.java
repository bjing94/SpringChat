package org.bjing.chat.profile;

import lombok.AllArgsConstructor;
import org.bjing.chat.auth.AuthResponse;
import org.bjing.chat.chat.ChatService;
import org.bjing.chat.chat.dto.ChatResponse;
import org.bjing.chat.common.CodeGenerator;
import org.bjing.chat.common.enums.FriendRequestStatus;
import org.bjing.chat.config.JwtService;
import org.bjing.chat.db.entity.Chat;
import org.bjing.chat.db.entity.FriendRequest;
import org.bjing.chat.db.entity.User;
import org.bjing.chat.db.repository.FriendRequestRepository;
import org.bjing.chat.db.repository.UserRepository;
import org.bjing.chat.profile.dto.*;
import org.bjing.chat.redis.EmailOtp;
import org.bjing.chat.redis.EmailOtpRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;

    private final EmailOtpRepository emailOtpRepository;

    private final JwtService jwtService;

    private final ChatService chatService;

    private final FriendRequestRepository friendRequestRepository;

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

    public ProfileResponse updateAvatar(String userId, MultipartFile file) {
        Optional<User> optionalUser = this.userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unable to get profile");
        User user = optionalUser.get();


        this.userRepository.save(user);

        return ProfileResponse.builder().email(user.getEmail()).firstname(user.getFirstname()).lastname(user.getLastname()).build();
    }

    public ProfileResponse updateEmail(String userId, ProfileUpdateEmailRequest dto) {
        Optional<User> optionalUser = this.userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unable to get profile");
        User user = optionalUser.get();

        String otpCode = CodeGenerator.generateOtpCode();

        this.emailOtpRepository.save(new EmailOtp(user.getEmail(), otpCode, dto.getEmail()));

        return ProfileResponse.builder().email(user.getEmail()).firstname(user.getFirstname()).lastname(user.getLastname()).build();
    }

    public AuthResponse confirmEmailUpdate(String userId, ProfileConfirmEmailUpdateRequest dto) {
        String code = dto.getCode();
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

        String token = this.jwtService.generateToken(user);
        return AuthResponse.builder().token(token).build();
    }

    public List<ChatResponse> getUserChats(String userId) {
        return this.chatService.findUserChats(userId);
    }

    public FriendRequestCreatedResponse sendFriendRequest(String userId, String receiverId) {
        if (userId.equals(receiverId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cant add yourself to friend list");

        User sender = this.userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        User receiver = this.userRepository.findById(receiverId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

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
    //       TODO
//    public FriendRequestCreatedResponse acceptFriendRequest(String userId, String receiverId) {
//
//
//    }
//
    //       TODO
//    public FriendRequestCreatedResponse declineFriendRequest(String userId, String receiverId) {
//
//
//    }
}
