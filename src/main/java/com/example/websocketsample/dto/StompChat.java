package com.example.websocketsample.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StompChat {
    private String name;
    private String message;
    private LocalDateTime time;
}
