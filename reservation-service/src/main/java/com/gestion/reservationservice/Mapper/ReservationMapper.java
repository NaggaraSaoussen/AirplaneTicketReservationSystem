package com.gestion.reservationservice.Mapper;

import com.gestion.reservationservice.dto.ReservationDTO;
import com.gestion.reservationservice.entity.Reservation;
import com.gestion.reservationservice.entity.ReservationStatus;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {
    public ReservationDTO toDTO(Reservation r) {
        return ReservationDTO.builder()
                .id(r.getId())
                .flightId(r.getFlightId())
                .email(r.getEmail())
                .seats(r.getSeats())
                .status(r.getStatus().name())
                .createdAt(r.getCreatedAt())
                .build();
    }

    public Reservation toEntity(ReservationDTO dto) {
        return Reservation.builder()
                .id(dto.getId())
                .flightId(dto.getFlightId())
                .email(dto.getEmail())
                .seats(dto.getSeats())
                .status(dto.getStatus() == null ? ReservationStatus.CONFIRMED : ReservationStatus.valueOf(dto.getStatus()))
                .createdAt(dto.getCreatedAt())
                .build();
    }
}

