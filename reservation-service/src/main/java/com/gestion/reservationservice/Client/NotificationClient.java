package com.gestion.reservationservice.Client;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 🔔 NotificationClient
 *
 * Ce client REST permet au reservation-service
 * de communiquer avec le notification-service.
 *
 * Il est utilisé pour envoyer des notifications
 * (ex : confirmation de réservation, annulation, etc.)
 * après une action métier.
 */
@Service
public class NotificationClient {

    // RestTemplate utilisé pour effectuer les appels HTTP inter-services
    private final RestTemplate restTemplate = new RestTemplate();

    // URL du notification-service
    private final String NOTIF_URL = "http://localhost:8082/api/notifications";

    /**
     * 📤 Envoi d'une notification vers le notification-service
     *
     * @param email       email de l'utilisateur à notifier
     * @param title       titre de la notification
     * @param message     contenu de la notification
     * @param type        type de notification (INFO, SUCCESS, ERROR, etc.)
     * @param bearerToken token JWT transmis pour l'authentification
     */
    public void send(String email, String title, String message, String type, String bearerToken) {

        // Création des headers HTTP
        HttpHeaders headers = new HttpHeaders();

        // Indication que le corps de la requête est au format JSON
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Ajout du token JWT dans le header Authorization
        // Important : le token doit toujours être envoyé sous la forme "Bearer <token>"
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            headers.set("Authorization", bearerToken);
        } else {
            headers.set("Authorization", "Bearer " + bearerToken);
        }

        // Corps de la requête contenant les informations de la notification
        Map<String, Object> body = Map.of(
                "email", email,
                "title", title,
                "message", message,
                "type", type
        );

        // Encapsulation du body et des headers dans une entité HTTP
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        // Envoi de la requête POST vers le notification-service
        restTemplate.exchange(NOTIF_URL, HttpMethod.POST, entity, Void.class);
    }
}
