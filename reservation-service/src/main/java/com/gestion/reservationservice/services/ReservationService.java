package com.gestion.reservationservice.services;

import com.gestion.reservationservice.dto.ReservationDTO;
import java.util.List;

public interface ReservationService {
    ReservationDTO create(Long flightId, int seats, String email, String bearerToken);
    List<ReservationDTO> myReservations(String email);

    ReservationDTO cancel(Long reservationId, String email, String bearerToken);
}
