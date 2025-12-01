package com.gestion.reservationservice.services;

import com.gestion.reservationservice.entity.Reservation;

import java.util.List;

public interface ReservationService {
    Reservation createReservation(Long passengerId, Long flightId, int seats);
    List<Reservation> getAll();
    Reservation getById(Long id);
}

