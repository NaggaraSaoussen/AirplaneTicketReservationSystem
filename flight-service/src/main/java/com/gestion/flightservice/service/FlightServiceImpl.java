package com.gestion.flightservice.service;

import com.gestion.flightservice.DTO.FlightDTO;
import com.gestion.flightservice.Mapper.FlightMapper;
import com.gestion.flightservice.Repository.FlightRepository;
import com.gestion.flightservice.entity.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final FlightMapper mapper;

    @Override
    public List<FlightDTO> getAll() {
        return flightRepository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public FlightDTO getById(Long id) {
        return mapper.toDTO(flightRepository.findById(id).orElseThrow());
    }

    @Override
    public FlightDTO create(FlightDTO dto) {
        Flight flight = mapper.toEntity(dto);
        return mapper.toDTO(flightRepository.save(flight));
    }

    @Override
    public FlightDTO update(Long id, FlightDTO dto) {
        Flight existing = flightRepository.findById(id).orElseThrow();

        existing.setDeparture(dto.getDeparture());
        existing.setArrival(dto.getArrival());
        existing.setDepartureTime(dto.getDepartureTime());
        existing.setArrivalTime(dto.getArrivalTime());
        existing.setPrice(dto.getPrice());
        existing.setSeatsAvailable(dto.getSeatsAvailable());

        return mapper.toDTO(flightRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        flightRepository.deleteById(id);
    }
}
