package com.gestion.reservationservice.clients.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FlightDTO {
    private Long id;
    private String departure;
    private String arrival;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double price;
    private int seatsAvailable;
}
