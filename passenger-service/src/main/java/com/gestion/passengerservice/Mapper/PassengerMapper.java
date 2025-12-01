package com.gestion.passengerservice.Mapper;

import com.gestion.passengerservice.DTO.PassengerDTO;
import com.gestion.passengerservice.entity.Passenger;
import org.springframework.stereotype.Component;

@Component
public class PassengerMapper {

    public PassengerDTO toDTO(Passenger p) {
        PassengerDTO dto = new PassengerDTO();
        dto.setId(p.getId());
        dto.setFirstName(p.getFirstName());
        dto.setLastName(p.getLastName());
        dto.setEmail(p.getEmail());
        dto.setPassportNumber(p.getPassportNumber());

        return dto;
    }

    public Passenger toEntity(PassengerDTO dto) {
        return Passenger.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .passportNumber(dto.getPassportNumber())

                .build();
    }
}
