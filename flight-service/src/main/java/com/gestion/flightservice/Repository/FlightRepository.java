package com.gestion.flightservice.Repository;

import com.gestion.flightservice.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    boolean existsByFlightNumber(String flightNumber);

    Optional<Flight> findByFlightNumber(String flightNumber);

    List<Flight> findByDepartureIgnoreCase(String departure);

    List<Flight> findByArrivalIgnoreCase(String arrival);

    List<Flight> findByDepartureIgnoreCaseAndArrivalIgnoreCase(String departure, String arrival);

    List<Flight> findByDepartureTimeBetween(LocalDateTime start, LocalDateTime end);

    List<Flight> findByDepartureIgnoreCaseAndArrivalIgnoreCaseAndDepartureTimeBetween(
            String departure, String arrival, LocalDateTime start, LocalDateTime end
    );
}
