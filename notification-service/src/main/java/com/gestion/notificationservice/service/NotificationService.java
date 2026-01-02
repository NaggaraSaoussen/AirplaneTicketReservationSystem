package com.gestion.notificationservice.service;

import com.gestion.notificationservice.dto.NotificationDTO;
import java.util.List;

public interface NotificationService {
    NotificationDTO create(NotificationDTO dto);
    List<NotificationDTO> getMyNotifications(String email);
    NotificationDTO createForUser(String email, String title, String message, String type);

    List<NotificationDTO> getAll();
}
