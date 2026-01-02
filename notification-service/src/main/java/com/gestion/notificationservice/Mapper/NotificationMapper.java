package com.gestion.notificationservice.Mapper;

import com.gestion.notificationservice.dto.NotificationDTO;
import com.gestion.notificationservice.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public Notification toEntity(NotificationDTO dto){
        return Notification.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .title(dto.getTitle())
                .message(dto.getMessage())
                .type(dto.getType())
                .createdAt(dto.getCreatedAt())
                .build();
    }

    public NotificationDTO toDTO(Notification n){
        return NotificationDTO.builder()
                .id(n.getId())
                .email(n.getEmail())
                .title(n.getTitle())
                .message(n.getMessage())
                .type(n.getType())
                .createdAt(n.getCreatedAt())
                .build();
    }
}
