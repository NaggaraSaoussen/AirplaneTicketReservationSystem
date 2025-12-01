package com.gestion.flightservice.Mapper;


import com.gestion.flightservice.DTO.FlightDTO;
import com.gestion.flightservice.entity.Flight;
import org.springframework.stereotype.Component;

@Component
public class FlightMapper {

    public FlightDTO toDTO(Flight f) {
        FlightDTO dto = new FlightDTO();
        dto.setId(f.getId());
        dto.setDeparture(f.getDeparture());
        dto.setArrival(f.getArrival());
        dto.setDepartureTime(f.getDepartureTime());
        dto.setArrivalTime(f.getArrivalTime());
        dto.setPrice(f.getPrice());
        dto.setSeatsAvailable(f.getSeatsAvailable());
        return dto;
    }

    public Flight toEntity(FlightDTO dto) {
        return Flight.builder()
                .id(dto.getId())
                .departure(dto.getDeparture())
                .arrival(dto.getArrival())
                .departureTime(dto.getDepartureTime())
                .arrivalTime(dto.getArrivalTime())
                .price(dto.getPrice())
                .seatsAvailable(dto.getSeatsAvailable())
                .build();
    }
}

