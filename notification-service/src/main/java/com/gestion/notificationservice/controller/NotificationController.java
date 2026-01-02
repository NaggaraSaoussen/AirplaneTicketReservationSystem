package com.gestion.notificationservice.controller;

import com.gestion.notificationservice.dto.NotificationDTO;
import com.gestion.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 🔔 NotificationController
 *
 * Ce contrôleur gère les notifications du système.
 * Il permet :
 *  - à un utilisateur authentifié de consulter ses propres notifications
 *  - à l’administrateur de consulter toutes les notifications
 *  - aux autres microservices (ex : reservation-service) de créer des notifications
 *
 * L’accès aux endpoints est sécurisé par les rôles USER et ADMIN.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    // Service métier responsable de la logique des notifications
    private final NotificationService service;

    /**
     * 📩 Consulter mes notifications
     *
     * L'utilisateur connecté récupère uniquement ses propres notifications.
     * L'identité de l'utilisateur est récupérée depuis le contexte de sécurité
     * via l'objet Authentication.
     *
     * Accessible par USER et ADMIN.
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/me")
    public List<NotificationDTO> me(Authentication auth){
        return service.getMyNotifications(auth.getName());
    }

    /**
     * 📋 Consulter toutes les notifications du système
     *
     * Cet endpoint est réservé uniquement à l’administrateur,
     * par exemple pour le suivi ou l’audit du système.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<NotificationDTO> all(){
        return service.getAll();
    }

    /**
     * ➕ Créer une notification
     *
     * Cet endpoint est principalement utilisé en interne par
     * d'autres microservices (ex : reservation-service)
     * afin de notifier un utilisateur après une action (réservation, annulation, etc.).
     *
     * Accessible par USER et ADMIN.
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public NotificationDTO create(@RequestBody NotificationDTO dto){
        return service.create(dto);
    }
}
