package com.gestion.reservationservice.services;

import com.gestion.flightservice.DTO.FlightDTO;
import com.gestion.reservationservice.Client.FlightClient;
import com.gestion.reservationservice.Client.NotificationClient;
import com.gestion.reservationservice.Mapper.ReservationMapper;
import com.gestion.reservationservice.dto.ReservationDTO;
import com.gestion.reservationservice.entity.Reservation;
import com.gestion.reservationservice.entity.ReservationStatus;
import com.gestion.reservationservice.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 🧾 ReservationServiceImpl
 *
 * Implémentation de la logique métier liée aux réservations.
 * Cette classe orchestre plusieurs microservices :
 *  - flight-service (gestion des sièges)
 *  - notification-service (notifications + emails)
 *
 * Elle assure la cohérence du processus de réservation et d’annulation.
 */
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    // Repository pour l'accès aux données de réservation
    private final ReservationRepository repo;

    // Mapper pour convertir Entity <-> DTO
    private final ReservationMapper mapper;

    // Client REST vers le flight-service
    private final FlightClient flightClient;

    // Client REST vers le notification-service
    private final NotificationClient notificationClient;

    // Format d'affichage des dates dans les messages envoyés aux utilisateurs
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * ➕ Créer une nouvelle réservation
     *
     * Étapes du processus :
     *  1. Réserver les sièges dans le flight-service
     *  2. Enregistrer la réservation en base de données
     *  3. Récupérer les détails du vol
     *  4. Envoyer une notification + email de confirmation
     */
    @Override
    public ReservationDTO create(Long flightId, int seats, String email, String bearerToken) {

        // 1) Réserver les places dans le flight-service
        flightClient.reserveSeats(flightId, seats, bearerToken);

        // 2) Création et sauvegarde de la réservation
        Reservation r = Reservation.builder()
                .flightId(flightId)
                .email(email)
                .seats(seats)
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.now())
                .build();

        Reservation saved = repo.save(r);

        // 3) Récupération des détails du vol
        FlightDTO f = flightClient.getFlight(flightId, bearerToken);

        // 4) Construction d'un message détaillé pour la notification et l'email
        String msg = "Votre réservation est confirmée ✅\n"
                + "Réservation #" + saved.getId() + "\n"
                + "Vol: " + f.getFlightNumber() + " (" + f.getDeparture() + " → " + f.getArrival() + ")\n"
                + "Départ: " + f.getDepartureTime().format(FMT) + "\n"
                + "Arrivée: " + f.getArrivalTime().format(FMT) + "\n"
                + "Places: " + seats;

        // Envoi de la notification de confirmation
        notificationClient.send(
                email,
                "Reservation confirmed",
                msg,
                "CONFIRMATION",
                bearerToken
        );

        // Retour du DTO de la réservation créée
        return mapper.toDTO(saved);
    }

    /**
     * 📋 Récupérer les réservations de l'utilisateur connecté
     *
     * Les réservations sont triées par date de création décroissante.
     */
    @Override
    public List<ReservationDTO> myReservations(String email) {
        return repo.findByEmailOrderByCreatedAtDesc(email)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    /**
     * ❌ Annuler une réservation existante
     *
     * Étapes du processus :
     *  1. Vérification de l'existence et de la propriété de la réservation
     *  2. Libération des sièges dans le flight-service
     *  3. Mise à jour du statut de la réservation
     *  4. Envoi d'une notification + email d’annulation
     */
    @Override
    public ReservationDTO cancel(Long reservationId, String email, String bearerToken) {

        // Recherche de la réservation
        Reservation r = repo.findById(reservationId)
                .orElseThrow(() ->
                        new RuntimeException("Reservation not found: " + reservationId));

        // Vérification que la réservation appartient bien à l'utilisateur
        if (!r.getEmail().equals(email))
            throw new RuntimeException("Forbidden: not your reservation");

        // Si la réservation est déjà annulée, on la retourne directement
        if (r.getStatus() == ReservationStatus.CANCELLED)
            return mapper.toDTO(r);

        // 1) Libération des places dans le flight-service
        flightClient.releaseSeats(r.getFlightId(), r.getSeats(), bearerToken);

        // 2) Mise à jour du statut et sauvegarde
        r.setStatus(ReservationStatus.CANCELLED);
        Reservation saved = repo.save(r);

        // 3) Récupération des détails du vol pour le message
        FlightDTO f = flightClient.getFlight(r.getFlightId(), bearerToken);

        // Construction du message d'annulation
        String msg = "Votre réservation a été annulée ❌\n"
                + "Réservation #" + saved.getId() + "\n"
                + "Vol: " + f.getFlightNumber() + " (" + f.getDeparture() + " → " + f.getArrival() + ")\n"
                + "Départ prévu: " + f.getDepartureTime().format(FMT) + "\n"
                + "Places annulées: " + saved.getSeats();

        // Envoi de la notification d’annulation
        notificationClient.send(
                email,
                "Reservation cancelled",
                msg,
                "CANCELLATION",
                bearerToken
        );

        // Retour du DTO mis à jour
        return mapper.toDTO(saved);
    }
}
