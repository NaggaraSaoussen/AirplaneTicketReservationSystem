package com.gestion.notificationservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;        // destinataire
    private String title;        // ex: Reservation confirmed
    private String message;      // texte
    private String type;         // CONFIRMATION / CANCELLATION
    private LocalDateTime createdAt;
}
