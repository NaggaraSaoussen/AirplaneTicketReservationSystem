package com.gestion.flightservice.service;

import com.gestion.flightservice.DTO.FlightDTO;

import java.util.List;

public interface FlightService {
    List<FlightDTO> getAll();
    FlightDTO getById(Long id);
    FlightDTO create(FlightDTO dto);
    FlightDTO update(Long id, FlightDTO dto);
    void delete(Long id);
}
