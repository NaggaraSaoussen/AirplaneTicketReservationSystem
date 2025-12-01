package com.gestion.reservationservice.clients.dto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "flight-service")
public interface FlightClient {

    @GetMapping("/api/flights/{id}")
    FlightDTO getFlight(@PathVariable Long id);
}

