package com.example.websocketsample.service;

import com.example.websocketsample.dto.StompChat;
import com.example.websocketsample.persist.entity.ChatEntity;
import com.example.websocketsample.persist.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatRepository chatRepository;

    private static final Queue<StompChat> chatList = new ConcurrentLinkedQueue<>();
    private static long lastIndex;
    private static final int CHAT_CACHE_SIZE = 10;

    private static final DateTimeFormatter customTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public List<StompChat> getRecentlyChat() {
        return new ArrayList<>(chatList);
    }

    public void saveChat(StompChat chat) {
        chat.setTime(LocalDateTime.parse(LocalDateTime.now().format(customTimeFormatter),customTimeFormatter));

        chatList.add(chat);

        if (chatList.size() == CHAT_CACHE_SIZE) {
            StompChat targetChat = chatList.poll();

            lastIndex = chatRepository.save(ChatEntity.builder()
                    .name(targetChat.getName())
                    .message(targetChat.getMessage())
                    .chatTime(targetChat.getTime())
                    .build()).getId();
        }
    }

}
