package org.bjing.chat.chat;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bjing.chat.chat.dto.*;
import org.bjing.chat.common.PaginationRequest;
import org.bjing.chat.common.PaginationResponse;
import org.bjing.chat.db.entity.User;
import org.bjing.chat.file.FileCreatedResponse;
import org.bjing.chat.file.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final FileService fileService;
    private final ChatService chatService;

    @GetMapping("/{id}")
    public ResponseEntity<ChatResponse> get(@PathVariable("id") String id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(this.chatService.get(id, user.getId()));
    }

    @PostMapping("/image")
    public ResponseEntity<FileCreatedResponse> uploadImage(@RequestParam("file") MultipartFile file) {
        FileCreatedResponse result = this.fileService.saveFileLocally(file);
        return ResponseEntity.ok(result);
    }

    @PostMapping()
    public ResponseEntity<ChatCreateResponse> create(@RequestBody() ChatCreateRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(this.chatService.create(request, user.getId()));
    }

    @PostMapping("/{id}/message")
    public ResponseEntity<MessageCreatedResponse> sendMessage(@PathVariable("id") String id,
                                                              @Valid MessageCreateRequest request,
                                                              @RequestParam(value = "file", required = false) MultipartFile file,
                                                              Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(this.chatService.sendMessage(new MessageCreateDto(user.getId(), id, request.getContent(), Optional.ofNullable(file))));
    }

    @GetMapping("/{id}/messages")
    public ResponseEntity<PaginationResponse<Set<MessageCreatedResponse>>> getChatMessages(@PathVariable("id") String id,
                                                                                           PaginationRequest pagination,
                                                                                           Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(this.chatService.getChatMessages(user.getId(), id, pagination));
    }
}
