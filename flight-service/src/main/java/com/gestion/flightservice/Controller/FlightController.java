package com.gestion.flightservice.Controller;

import com.gestion.flightservice.DTO.FlightDTO;
import com.gestion.flightservice.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * ✈️ FlightController
 *
 * Ce contrôleur expose les endpoints REST pour la gestion des vols.
 * Il permet :
 *  - la consultation des vols (USER + ADMIN)
 *  - la réservation et libération des sièges
 *  - la recherche de vols avec des critères
 *  - la gestion complète des vols (ADMIN uniquement)
 *
 * La sécurité est gérée via @PreAuthorize selon les rôles.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flights")
public class FlightController {

    // Service métier contenant la logique de gestion des vols
    private final FlightService service;

    /**
     * 🔍 Récupérer la liste de tous les vols
     * Accessible par USER et ADMIN
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    public List<FlightDTO> getAll() {
        return service.getAll();
    }

    /**
     * 🔍 Récupérer un vol par son identifiant
     * Accessible par USER et ADMIN
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public FlightDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    /**
     * 🔍 Récupérer un vol par son numéro de vol
     * Accessible par USER et ADMIN
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/number/{flightNumber}")
    public FlightDTO getByFlightNumber(@PathVariable String flightNumber) {
        return service.getByFlightNumber(flightNumber);
    }

    /**
     * 🪑 Réserver un nombre de sièges pour un vol donné
     * Accessible par USER et ADMIN
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{id}/reserve")
    public ResponseEntity<?> reserve(@PathVariable Long id, @RequestParam int seats) {
        FlightDTO updated = service.reserveSeats(id, seats);
        return ResponseEntity.ok(Map.of("message", "Seats reserved successfully", "flight", updated));
    }

    /**
     * 🪑 Libérer (annuler) un nombre de sièges réservés
     * Accessible par USER et ADMIN
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{id}/release")
    public ResponseEntity<?> release(@PathVariable Long id, @RequestParam int seats) {
        FlightDTO updated = service.releaseSeats(id, seats);
        return ResponseEntity.ok(Map.of("message", "Seats released successfully", "flight", updated));
    }

    /**
     * 🔎 Recherche de vols avec critères dynamiques
     *
     * Exemples :
     * /api/flights/search?departure=Tunis
     * /api/flights/search?arrival=Paris
     * /api/flights/search?date=2025-12-27
     * /api/flights/search?departure=Tunis&arrival=Paris
     * /api/flights/search?departure=Tunis&arrival=Paris&date=2025-12-27
     *
     * Accessible par USER et ADMIN
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/search")
    public List<FlightDTO> search(
            @RequestParam(required = false) String departure,
            @RequestParam(required = false) String arrival,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return service.search(departure, arrival, date);
    }

    /**
     * ➕ Créer un nouveau vol
     * Accessible uniquement par ADMIN
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody FlightDTO dto) {
        FlightDTO created = service.create(dto);
        return ResponseEntity.ok(Map.of("message", "Flight created successfully", "flight", created));
    }

    /**
     * ✏️ Modifier les informations d’un vol existant
     * Accessible uniquement par ADMIN
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody FlightDTO dto) {
        FlightDTO updated = service.update(id, dto);
        return ResponseEntity.ok(Map.of("message", "Flight updated successfully", "flight", updated));
    }

    /**
     * 🗑️ Supprimer un vol
     * Accessible uniquement par ADMIN
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(Map.of("message", "Flight deleted successfully"));
    }
}
