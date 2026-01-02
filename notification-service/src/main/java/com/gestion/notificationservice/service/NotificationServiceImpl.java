package com.gestion.notificationservice.service;

import com.gestion.notificationservice.dto.NotificationDTO;
import com.gestion.notificationservice.entity.Notification;
import com.gestion.notificationservice.Mapper.NotificationMapper;
import com.gestion.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 🔔 NotificationServiceImpl
 *
 * Implémentation de la couche service pour la gestion des notifications.
 * Cette classe contient la logique métier :
 *  - création des notifications
 *  - enregistrement en base de données
 *  - envoi d'un email associé à la notification
 *  - consultation des notifications (par utilisateur ou globalement)
 */
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    // Repository permettant l'accès à la base de données
    private final NotificationRepository repo;

    // Mapper utilisé pour convertir Entity <-> DTO
    private final NotificationMapper mapper;

    // Service responsable de l'envoi des emails
    private final EmailService emailService;

    /**
     * ➕ Créer une notification à partir d'un DTO
     *
     * Étapes :
     *  1. Conversion du DTO en entité
     *  2. Ajout de la date de création
     *  3. Sauvegarde en base de données
     *  4. Envoi d’un email de notification à l’utilisateur
     *  5. Retour du DTO correspondant
     */
    @Override
    public NotificationDTO create(NotificationDTO dto) {

        // Conversion du DTO en entité Notification
        Notification n = mapper.toEntity(dto);

        // Initialisation de la date de création
        n.setCreatedAt(LocalDateTime.now());

        // Sauvegarde de la notification en base
        Notification saved = repo.save(n);

        // Envoi de l'email associé à la notification
        // (protégé par un try/catch pour ne pas bloquer le flux métier)
        try {
            emailService.send(saved.getEmail(), saved.getTitle(), saved.getMessage());
        } catch (Exception e) {
            System.out.println("EMAIL FAILED: " + e.getMessage());
        }

        // Retour du DTO correspondant à la notification sauvegardée
        return mapper.toDTO(saved);
    }

    /**
     * ➕ Créer une notification directement pour un utilisateur
     *
     * Cette méthode est utilisée lorsque les informations
     * ne proviennent pas directement d’un DTO (appel interne).
     */
    @Override
    public NotificationDTO createForUser(String email, String title, String message, String type) {

        // Construction manuelle de l'entité Notification
        Notification n = Notification.builder()
                .email(email)
                .title(title)
                .message(message)
                .type(type)
                .createdAt(LocalDateTime.now())
                .build();

        // Sauvegarde de la notification
        Notification saved = repo.save(n);

        // Envoi de l'email associé
        try {
            emailService.send(saved.getEmail(), saved.getTitle(), saved.getMessage());
        } catch (Exception e) {
            System.out.println("EMAIL FAILED: " + e.getMessage());
        }

        // Retour du DTO
        return mapper.toDTO(saved);
    }

    /**
     * 📩 Récupérer les notifications d’un utilisateur donné
     *
     * Les notifications sont triées par date de création décroissante.
     */
    @Override
    public List<NotificationDTO> getMyNotifications(String email) {
        return repo.findByEmailOrderByCreatedAtDesc(email)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    /**
     * 📋 Récupérer toutes les notifications du système
     *
     * Méthode réservée à l’administrateur.
     */
    @Override
    public List<NotificationDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}
