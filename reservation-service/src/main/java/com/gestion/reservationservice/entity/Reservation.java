package com.gestion.reservationservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long passengerId;   // ID du passager dans passenger-service
    private Long flightId;      // ID du vol dans flight-service

    private LocalDate reservationDate;

    private int seats;          // nombre de sièges réservés
}
