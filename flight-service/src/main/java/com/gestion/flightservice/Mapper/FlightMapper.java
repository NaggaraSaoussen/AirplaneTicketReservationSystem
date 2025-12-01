package com.gestion.flightservice.Mapper;

import com.gestion.flightservice.DTO.FlightDTO;
import com.gestion.flightservice.entity.Flight;
import org.springframework.stereotype.Component;

@Component
public class FlightMapper {

    public FlightDTO toDTO(Flight f) {
        if (f == null) return null;
        return FlightDTO.builder()
                .id(f.getId())
                .flightNumber(f.getFlightNumber())
                .departure(f.getDeparture())
                .arrival(f.getArrival())
                .departureTime(f.getDepartureTime())
                .arrivalTime(f.getArrivalTime())
                .price(f.getPrice())
                .seatsAvailable(f.getSeatsAvailable())
                .build();
    }

    public Flight toEntity(FlightDTO dto) {
        if (dto == null) return null;
        return Flight.builder()
                .id(dto.getId())
                .flightNumber(dto.getFlightNumber())
                .departure(dto.getDeparture())
                .arrival(dto.getArrival())
                .departureTime(dto.getDepartureTime())
                .arrivalTime(dto.getArrivalTime())
                .price(dto.getPrice())
                .seatsAvailable(dto.getSeatsAvailable())
                .build();
    }
}
