package com.gestion.reservationservice.repository;

import com.gestion.reservationservice.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByEmailOrderByCreatedAtDesc(String email);
}
