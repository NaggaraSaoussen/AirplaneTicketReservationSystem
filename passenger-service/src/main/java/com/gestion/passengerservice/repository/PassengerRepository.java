package com.gestion.passengerservice.repository;

import com.gestion.passengerservice.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
