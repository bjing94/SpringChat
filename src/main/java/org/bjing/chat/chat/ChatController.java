package org.bjing.chat.chat;

import lombok.RequiredArgsConstructor;
import org.bjing.chat.chat.dto.*;
import org.bjing.chat.db.entity.User;
import org.bjing.chat.file.FileCreatedResponse;
import org.bjing.chat.file.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
                                                              @RequestBody() MessageCreateRequest request,
                                                              Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(this.chatService.sendMessage(id, user.getId(), request.getContent()));
    }
}
