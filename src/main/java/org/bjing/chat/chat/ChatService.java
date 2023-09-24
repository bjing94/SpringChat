package org.bjing.chat.chat;

import lombok.AllArgsConstructor;
import org.bjing.chat.chat.dto.*;
import org.bjing.chat.chat.mapper.MessageResponseMapper;
import org.bjing.chat.db.entity.Chat;
import org.bjing.chat.db.entity.Media;
import org.bjing.chat.db.entity.Message;
import org.bjing.chat.db.entity.User;
import org.bjing.chat.db.repository.ChatRepository;
import org.bjing.chat.db.repository.MediaRepository;
import org.bjing.chat.db.repository.MessageRepository;
import org.bjing.chat.db.repository.UserRepository;
import org.bjing.chat.file.FileCreatedResponse;
import org.bjing.chat.file.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChatService {
    private ChatRepository chatRepository;
    private UserRepository userRepository;

    private MessageRepository messageRepository;

    private MediaRepository mediaRepository;

    private FileService fileService;

    public ChatCreateResponse create(ChatCreateRequest request, String creatorUUID) {
        List<String> userIds = request.getUserIds();

        List<User> users = this.userRepository.findAllById(userIds);
        Set<User> userSet = new HashSet<>(users);
        Optional<User> chatCreator = this.userRepository.findById(creatorUUID);

        if (users.isEmpty() || chatCreator.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Users not found!");
        }
        users.stream().forEach(item -> {
            System.out.println(item.getUsername());
        });

        Chat chat = Chat.builder()
                .users(userSet)
                .creator(chatCreator.get())
                .build();

        Chat newChat = this.chatRepository.save(chat);
        return ChatCreateResponse.builder().creatorId(creatorUUID.toString())
                .userIds(userIds).id(newChat.getId()).build();
    }

    public ChatResponse get(String chatUUID, String userUUID) {
        Optional<Chat> chatOptional = this.chatRepository.findById(chatUUID);
        if (chatOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found!");
        }

        Chat chat = chatOptional.get();

        this.checkChatAccess(chat, userUUID);

        return ChatResponse.builder()
                .id(chat.getId())
                .creatorId(chat.getCreator().getId())
                .userIds(chat.getUsers().stream().map(User::getId).collect(Collectors.toSet()))
                .mediaFiles(chat.getMediaFiles())
                .messages(chat.getMessages()
                        .stream()
                        .map(MessageResponseMapper::toResponse)
                        .collect(Collectors.toSet()))
                .created(chat.getCreated())
                .updated(chat.getUpdated())
                .build();
    }

    public MessageCreatedResponse sendMessage(MessageCreateDto dto) {
        String chatId = dto.getChatId();
        String userId = dto.getUserId();
        String content = dto.getContent();
        MultipartFile file = dto.getFile();

        Optional<Chat> optionalChat = this.chatRepository.findById(chatId);
        Optional<User> optionalUser = this.userRepository.findById(userId);
        if (optionalChat.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Chat chat = optionalChat.get();
        User user = optionalUser.get();

        this.checkChatAccess(chat, user.getId());
        Set<Media> mediaFiles = new HashSet<>();


        Message messageEntity = Message.builder()
                .chat(chat)
                .user(user)
                .content(content)
                .build();

        if (!file.isEmpty()) {
            FileCreatedResponse data = this.fileService.saveFileLocally(file);

            Media media = Media.builder()
                    .link(data.getLink())
                    .chat(chat)
                    .message(messageEntity)
                    .build();

            mediaFiles.add(media);
        }

        messageEntity.setMediaFiles(mediaFiles);


        Message savedMessage = this.messageRepository.save(messageEntity);


        return MessageResponseMapper.toResponse(savedMessage);
    }

    private void checkChatAccess(Chat chat, String userId) {
        Set<User> users = chat.getUsers();

        Optional<User> userInChat = users.stream().filter(item -> item.getId().equals(userId)).findFirst();
        if (userInChat.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are not in chat!");
        }
    }
}
