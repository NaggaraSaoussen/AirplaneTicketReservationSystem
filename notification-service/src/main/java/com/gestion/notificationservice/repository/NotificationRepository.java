package com.gestion.notificationservice.repository;

import com.gestion.notificationservice.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByEmailOrderByCreatedAtDesc(String email);
}

