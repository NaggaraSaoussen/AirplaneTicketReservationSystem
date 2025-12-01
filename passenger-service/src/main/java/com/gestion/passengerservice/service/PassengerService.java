package com.gestion.passengerservice.service;

import com.gestion.passengerservice.DTO.PassengerDTO;

import java.util.List;

public interface PassengerService {

    List<PassengerDTO> getAll();
    PassengerDTO getById(Long id);
    PassengerDTO create(PassengerDTO dto);
    PassengerDTO update(Long id, PassengerDTO dto);
    void delete(Long id);
}
