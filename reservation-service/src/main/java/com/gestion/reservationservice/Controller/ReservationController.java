package com.gestion.reservationservice.Controller;

import com.gestion.reservationservice.dto.ReservationDTO;
import com.gestion.reservationservice.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService service;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(
            @RequestParam Long flightId,
            @RequestParam int seats,
            Authentication auth,
            @RequestHeader("Authorization") String bearerToken
    ) {
        String email = auth.getName();
        ReservationDTO created = service.create(flightId, seats, email, bearerToken);
        return ResponseEntity.ok(Map.of(
                "message", "Reservation created",
                "reservation", created,
                "notificationSent", true
        ));
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/me")
    public List<ReservationDTO> myReservations(Authentication auth) {
        return service.myReservations(auth.getName());
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(
            @PathVariable Long id,
            Authentication auth,
            @RequestHeader("Authorization") String bearerToken
    ) {
        String email = auth.getName();
        ReservationDTO cancelled = service.cancel(id, email, bearerToken);
        return ResponseEntity.ok(Map.of("message", "Reservation cancelled", "reservation", cancelled));
    }
}
