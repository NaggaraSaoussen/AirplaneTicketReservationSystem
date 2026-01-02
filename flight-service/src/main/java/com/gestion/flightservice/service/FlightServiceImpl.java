package com.gestion.flightservice.service;

import com.gestion.flightservice.DTO.FlightDTO;
import com.gestion.flightservice.Mapper.FlightMapper;
import com.gestion.flightservice.Repository.FlightRepository;
import com.gestion.flightservice.entity.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));
        return mapper.toDTO(flight);
    }

    @Override
    public FlightDTO getByFlightNumber(String flightNumber) {
        Flight flight = flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new RuntimeException("Flight not found with flightNumber: " + flightNumber));
        return mapper.toDTO(flight);
    }

    @Override
    public List<FlightDTO> search(String departure, String arrival, LocalDate date) {

        LocalDateTime start = null;
        LocalDateTime end = null;

        if (date != null) {
            start = date.atStartOfDay();
            end = date.plusDays(1).atStartOfDay().minusNanos(1);
        }

        List<Flight> flights;

        // departure + arrival + date
        if (departure != null && arrival != null && start != null) {
            flights = flightRepository
                    .findByDepartureIgnoreCaseAndArrivalIgnoreCaseAndDepartureTimeBetween(
                            departure, arrival, start, end
                    );

            // departure + arrival
        } else if (departure != null && arrival != null) {
            flights = flightRepository.findByDepartureIgnoreCaseAndArrivalIgnoreCase(departure, arrival);

            // date only
        } else if (start != null) {
            flights = flightRepository.findByDepartureTimeBetween(start, end);

            // departure only
        } else if (departure != null) {
            flights = flightRepository.findByDepartureIgnoreCase(departure);

            // arrival only
        } else if (arrival != null) {
            flights = flightRepository.findByArrivalIgnoreCase(arrival);

            // none -> all
        } else {
            flights = flightRepository.findAll();
        }

        return flights.stream().map(mapper::toDTO).toList();
    }
    @Override
    public FlightDTO reserveSeats(Long id, int seats) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        if (seats <= 0) throw new RuntimeException("Seats must be > 0");

        if (flight.getSeatsAvailable() < seats)
            throw new RuntimeException("Not enough seats available");

        flight.setSeatsAvailable(flight.getSeatsAvailable() - seats);
        return mapper.toDTO(flightRepository.save(flight));
    }
    @Override
    public FlightDTO releaseSeats(Long id, int seats) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        if (seats <= 0) throw new RuntimeException("Seats must be > 0");

        flight.setSeatsAvailable(flight.getSeatsAvailable() + seats);
        return mapper.toDTO(flightRepository.save(flight));
    }




    @Override
    public FlightDTO create(FlightDTO dto) {
        if (dto.getFlightNumber() == null || dto.getFlightNumber().isBlank()) {
            throw new RuntimeException("flightNumber is required");
        }
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

        if (dto.getFlightNumber() != null && !dto.getFlightNumber().isBlank()) {
            if (!dto.getFlightNumber().equals(existing.getFlightNumber())
                    && flightRepository.existsByFlightNumber(dto.getFlightNumber())) {
                throw new RuntimeException("Flight number already exists: " + dto.getFlightNumber());
            }
            existing.setFlightNumber(dto.getFlightNumber());
        }

        if (dto.getDeparture() != null) existing.setDeparture(dto.getDeparture());
        if (dto.getArrival() != null) existing.setArrival(dto.getArrival());
        if (dto.getDepartureTime() != null) existing.setDepartureTime(dto.getDepartureTime());
        if (dto.getArrivalTime() != null) existing.setArrivalTime(dto.getArrivalTime());
        if (dto.getPrice() > 0) existing.setPrice(dto.getPrice());

        // important: seatsAvailable peut être 0, donc on vérifie >=0
        if (dto.getSeatsAvailable() >= 0) existing.setSeatsAvailable(dto.getSeatsAvailable());

        return mapper.toDTO(flightRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new RuntimeException("Flight not found with id: " + id);
        }
        flightRepository.deleteById(id);
    }
}
