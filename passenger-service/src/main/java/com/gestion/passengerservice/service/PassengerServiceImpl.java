package com.gestion.passengerservice.service;


import com.gestion.passengerservice.DTO.PassengerDTO;
import com.gestion.passengerservice.entity.Passenger;
import com.gestion.passengerservice.repository.PassengerRepository;
import com.gestion.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;

    private PassengerDTO toDTO(Passenger passenger) {
        PassengerDTO dto = new PassengerDTO();
        dto.setId(passenger.getId());
        dto.setFirstName(passenger.getFirstName());
        dto.setLastName(passenger.getLastName());
        dto.setEmail(passenger.getEmail());
        dto.setPassportNumber(passenger.getPassportNumber());
        dto.setDateOfBirth(passenger.getDateOfBirth());
        dto.setNationality(passenger.getNationality());
        dto.setUserId(passenger.getUserId());
        return dto;
    }

    private Passenger toEntity(PassengerDTO dto) {
        return Passenger.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .passportNumber(dto.getPassportNumber())
                .dateOfBirth(dto.getDateOfBirth())
                .nationality(dto.getNationality())
                .userId(dto.getUserId())
                .build();
    }

    @Override
    public PassengerDTO createPassenger(PassengerDTO dto) {
        Passenger passenger = passengerRepository.save(toEntity(dto));
        return toDTO(passenger);
    }

    @Override
    public PassengerDTO updatePassenger(Long id, PassengerDTO dto) {
        Passenger existing = passengerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        existing.setPassportNumber(dto.getPassportNumber());
        existing.setDateOfBirth(dto.getDateOfBirth());
        existing.setNationality(dto.getNationality());

        return toDTO(passengerRepository.save(existing));
    }

    @Override
    public PassengerDTO getPassenger(Long id) {
        return passengerRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));
    }

    @Override
    public List<PassengerDTO> getPassengersByUser(Long userId) {
        return passengerRepository.findByUserId(userId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public void deletePassenger(Long id) {
        passengerRepository.deleteById(id);
    }
}
