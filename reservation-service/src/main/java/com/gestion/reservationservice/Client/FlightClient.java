package com.gestion.reservationservice.Client;

import com.gestion.flightservice.DTO.FlightDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * ✈️ FlightClient
 *
 * Ce client REST permet au reservation-service
 * de communiquer avec le flight-service.
 *
 * Il est utilisé pour :
 *  - réserver des sièges sur un vol
 *  - libérer des sièges
 *  - récupérer les détails d’un vol
 *
 * La communication entre microservices est sécurisée
 * grâce au token JWT transmis dans le header Authorization.
 */
@Service
public class FlightClient {

    // RestTemplate utilisé pour effectuer les appels HTTP
    private final RestTemplate restTemplate = new RestTemplate();

    // URL de base du flight-service
    private final String FLIGHT_URL = "http://localhost:8081/api/flights";

    /**
     * 🔐 Normalisation du token Bearer
     *
     * Cette méthode garantit que le token JWT
     * est toujours envoyé avec le préfixe "Bearer ".
     */
    private String normalizeBearer(String bearerToken) {
        if (bearerToken == null) return null;
        return bearerToken.startsWith("Bearer ") ? bearerToken : "Bearer " + bearerToken;
    }

    /**
     * 🪑 Réserver des sièges sur un vol
     *
     * Appel du endpoint PUT /api/flights/{id}/reserve
     * du flight-service.
     */
    public void reserveSeats(Long flightId, int seats, String bearerToken) {

        // Création des headers avec le token JWT
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", normalizeBearer(bearerToken));

        // Création de l'entité HTTP (sans body)
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // Construction de l'URL avec le nombre de sièges à réserver
        String url = FLIGHT_URL + "/" + flightId + "/reserve?seats=" + seats;

        // Appel HTTP PUT vers le flight-service
        restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
    }

    /**
     * 🪑 Libérer des sièges précédemment réservés
     *
     * Appel du endpoint PUT /api/flights/{id}/release
     * du flight-service.
     */
    public void releaseSeats(Long flightId, int seats, String bearerToken) {

        // Création des headers avec le token JWT
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", normalizeBearer(bearerToken));

        // Création de l'entité HTTP
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // Construction de l'URL
        String url = FLIGHT_URL + "/" + flightId + "/release?seats=" + seats;

        // Appel HTTP PUT vers le flight-service
        restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
    }

    /**
     * 🔍 Récupérer les détails d’un vol
     *
     * Appel du endpoint GET /api/flights/{id}
     * afin d'obtenir les informations du vol
     * nécessaires au service de réservation.
     */
    public FlightDTO getFlight(Long flightId, String bearerToken) {

        // Création des headers avec le token JWT
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", normalizeBearer(bearerToken));

        // Création de l'entité HTTP
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // Construction de l'URL
        String url = FLIGHT_URL + "/" + flightId;

        // Appel HTTP GET vers le flight-service
        ResponseEntity<FlightDTO> res =
                restTemplate.exchange(url, HttpMethod.GET, entity, FlightDTO.class);

        // Retour des données du vol
        return res.getBody();
    }
}
