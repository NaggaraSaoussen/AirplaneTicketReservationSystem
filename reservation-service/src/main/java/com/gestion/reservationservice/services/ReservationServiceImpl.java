package com.gestion.reservationservice.services;

import com.gestion.reservationservice.clients.dto.FlightClient;
import com.gestion.reservationservice.clients.dto.FlightDTO;
import com.gestion.reservationservice.entity.Reservation;
import com.gestion.reservationservice.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;
    private final FlightClient flightClient;

    @Override
    public Reservation createReservation(Long passengerId, Long flightId, int seats) {

        // Vérifier si le vol existe
        FlightDTO flight = flightClient.getFlight(flightId);

        if (flight == null) {
            throw new RuntimeException("Flight not found");
        }

        if (flight.getSeatsAvailable() < seats) {
            throw new RuntimeException("Not enough seats available");
        }

        Reservation reservation = Reservation.builder()
                .passengerId(passengerId)
                .flightId(flightId)
                .reservationDate(LocalDate.now())
                .seats(seats)
                .build();

        return repository.save(reservation);
    }

    @Override
    public List<Reservation> getAll() {
        return repository.findAll();
    }

    @Override
    public Reservation getById(Long id) {
        return repository.findById(id).orElseThrow();
    }
}
