package com.gestion.passengerservice.service;

import com.gestion.passengerservice.DTO.PassengerDTO;
import com.gestion.passengerservice.Mapper.PassengerMapper;
import com.gestion.passengerservice.entity.Passenger;
import com.gestion.passengerservice.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper mapper;

    @Override
    public List<PassengerDTO> getAll() {
        return passengerRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public PassengerDTO getById(Long id) {
        Passenger p = passengerRepository.findById(id).orElseThrow();
        return mapper.toDTO(p);
    }

    @Override
    public PassengerDTO create(PassengerDTO dto) {
        Passenger p = mapper.toEntity(dto);
        return mapper.toDTO(passengerRepository.save(p));
    }

    @Override
    public PassengerDTO update(Long id, PassengerDTO dto) {
        Passenger existing = passengerRepository.findById(id).orElseThrow();

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        existing.setPassportNumber(dto.getPassportNumber());


        return mapper.toDTO(passengerRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        passengerRepository.deleteById(id);
    }
}
