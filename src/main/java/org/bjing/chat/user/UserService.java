package org.bjing.chat.user;

import lombok.AllArgsConstructor;
import org.bjing.chat.common.dto.PaginationMeta;
import org.bjing.chat.common.dto.PaginationRequest;
import org.bjing.chat.common.dto.PaginationResponse;
import org.bjing.chat.db.entity.User;
import org.bjing.chat.db.repository.UserRepository;
import org.bjing.chat.user.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getUser(String userId) {
        return this.userRepository.findById(userId)
                .map(user -> UserResponse.builder().firstname(user.getFirstname()).lastname(user.getLastname())
                        .id(userId).build())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public PaginationResponse<Set<UserResponse>> findUsers(PaginationRequest pagination) {
        Page<User> users = this.userRepository.findAll(PageRequest.of(pagination.getPage(), pagination.getPageSize()));
        Set<UserResponse> userSet = users.getContent().stream()
                .map(user -> UserResponse.builder().firstname(user.getFirstname()).lastname(user.getLastname()).id(user.getId()).build())
                .collect(Collectors.toSet());

        return new PaginationResponse<Set<UserResponse>>(userSet,
                new PaginationMeta(users.getPageable().getPageNumber(),
                        users.getPageable().getPageSize(),
                        users.getTotalPages()));
    }
}
