package com.gestion.reservationservice.Controller;

import com.gestion.reservationservice.entity.Reservation;
import com.gestion.reservationservice.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;

    @PostMapping
    public Reservation create(
            @RequestParam Long passengerId,
            @RequestParam Long flightId,
            @RequestParam int seats) {
        return service.createReservation(passengerId, flightId, seats);
    }

    @GetMapping
    public List<Reservation> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Reservation getById(@PathVariable Long id) {
        return service.getById(id);
    }
}

