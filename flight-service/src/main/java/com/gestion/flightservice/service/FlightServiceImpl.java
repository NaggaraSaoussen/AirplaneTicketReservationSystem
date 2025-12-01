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
        return flightRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public FlightDTO getById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));
        return mapper.toDTO(flight);
    }

    @Override
    public FlightDTO create(FlightDTO dto) {
        // Vérification unique flightNumber
        if (flightRepository.existsByFlightNumber(dto.getFlightNumber())) {
            throw new RuntimeException("Flight number already exists: " + dto.getFlightNumber());
        }
        Flight flight = mapper.toEntity(dto);
        return mapper.toDTO(flightRepository.save(flight));
    }

    @Override
    public FlightDTO update(Long id, FlightDTO dto) {
        Flight existing = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));

        // Ne mettre à jour que si le champ n'est pas null (ou valide)
        if (dto.getFlightNumber() != null && !dto.getFlightNumber().isEmpty()) {
            existing.setFlightNumber(dto.getFlightNumber());
        }
        if (dto.getDeparture() != null) {
            existing.setDeparture(dto.getDeparture());
        }
        if (dto.getArrival() != null) {
            existing.setArrival(dto.getArrival());
        }
        if (dto.getDepartureTime() != null) {
            existing.setDepartureTime(dto.getDepartureTime());
        }
        if (dto.getArrivalTime() != null) {
            existing.setArrivalTime(dto.getArrivalTime());
        }
        if (dto.getPrice() != 0) {
            existing.setPrice(dto.getPrice());
        }
        if (dto.getSeatsAvailable() != 0) {
            existing.setSeatsAvailable(dto.getSeatsAvailable());
        }

        return mapper.toDTO(flightRepository.save(existing));
    }


    @Override
    public void delete(Long id) {
        flightRepository.deleteById(id);
    }
}
