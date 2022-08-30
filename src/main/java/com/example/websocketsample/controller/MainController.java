package com.example.websocketsample.controller;

import com.example.websocketsample.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final ChatService chatService;

    @GetMapping("/")
    public String mainPage(Model model) {
        System.out.println("메인");

        model.addAttribute("list", chatService.getRecentlyChat());

        System.out.println(chatService.getRecentlyChat());
        return "main";
    }
}
