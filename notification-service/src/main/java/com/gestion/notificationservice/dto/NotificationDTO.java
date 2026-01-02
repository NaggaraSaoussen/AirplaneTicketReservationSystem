package com.gestion.notificationservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {
    private Long id;
    private String email;
    private String title;
    private String message;
    private String type;
    private LocalDateTime createdAt;
}

