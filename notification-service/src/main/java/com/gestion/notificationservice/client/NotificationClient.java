package com.gestion.notificationservice.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 🔗 NotificationClient
 *
 * Cette classe joue le rôle de client HTTP interne.
 * Elle permet à un microservice d’envoyer une notification
 * vers le notification-service via une requête REST.
 *
 * Elle est généralement appelée après une action métier
 * (ex : réservation, annulation, confirmation, etc.).
 */
@Service
public class NotificationClient {

    // RestTemplate utilisé pour effectuer des appels HTTP entre microservices
    private final RestTemplate restTemplate = new RestTemplate();

    // URL du notification-service (endpoint de création de notification)
    private final String NOTIF_URL = "http://localhost:8082/api/notifications";

    /**
     * 📤 Envoi d'une notification vers le notification-service
     *
     * @param email        email de l'utilisateur à notifier
     * @param title        titre de la notification
     * @param message      contenu de la notification
     * @param type         type de notification (ex : INFO, WARNING, SUCCESS)
     * @param bearerToken  token JWT transmis pour l'authentification inter-services
     */
    public void send(String email, String title, String message, String type, String bearerToken) {

        // Création des headers HTTP
        HttpHeaders headers = new HttpHeaders();

        // Ajout du token JWT dans le header Authorization
        // (le token est nécessaire pour passer la sécurité du notification-service)
        headers.set("Authorization", bearerToken.startsWith("Bearer ") ? bearerToken : "Bearer " + bearerToken);

        // Indication que le corps de la requête est au format JSON
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Corps de la requête contenant les informations de la notification
        Map<String,Object> body = Map.of(
                "email", email,
                "title", title,
                "message", message,
                "type", type
        );

        // Encapsulation du body + headers dans une entité HTTP
        HttpEntity<Map<String,Object>> entity = new HttpEntity<>(body, headers);

        // Envoi de la requête POST vers le notification-service
        restTemplate.exchange(NOTIF_URL, HttpMethod.POST, entity, Void.class);
    }
}
