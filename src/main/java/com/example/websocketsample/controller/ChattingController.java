package com.example.websocketsample.controller;

import com.example.websocketsample.service.ChatService;
import com.example.websocketsample.dto.StompChat;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChattingController {

    private final ChatService chatService;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings") //broadcast
    public StompChat greeting(StompChat chat) throws InterruptedException {
        System.out.println("GreetingController.greeting !" + chat);

        chatService.saveChat(chat);

        Thread.sleep(500); //비동기를 나타내기 위해

        System.out.println(chat);
        return chat;
    }
}