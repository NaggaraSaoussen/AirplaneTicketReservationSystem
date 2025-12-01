package com.gestion.flightservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String departure;

    private String arrival;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private double price;

    private int seatsAvailable;
}

