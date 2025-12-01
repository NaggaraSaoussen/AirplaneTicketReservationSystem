package com.gestion.flightservice.Repository;

import com.gestion.flightservice.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    boolean existsByFlightNumber(String flightNumber);

}
