package com.gestion.passengerservice.service;

import com.gestion.passengerservice.DTO.PassengerDTO;

import java.util.List;

public interface PassengerService {


    PassengerDTO createPassenger(PassengerDTO dto);

    PassengerDTO updatePassenger(Long id, PassengerDTO dto);

    PassengerDTO getPassenger(Long id);

    List<PassengerDTO> getPassengersByUser(Long userId);

    void deletePassenger(Long id);
}
