package com.gestion.flightservice.service;

import com.gestion.flightservice.DTO.FlightDTO;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {

    // USER + ADMIN
    List<FlightDTO> getAll();
    FlightDTO getById(Long id);
    FlightDTO getByFlightNumber(String flightNumber);
    FlightDTO reserveSeats(Long flightId, int seats);
    FlightDTO releaseSeats(Long flightId, int seats);

    List<FlightDTO> search(String departure, String arrival, LocalDate date);

    // ADMIN
    FlightDTO create(FlightDTO dto);
    FlightDTO update(Long id, FlightDTO dto);
    void delete(Long id);
}
