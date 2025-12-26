package com.gestion.passengerservice.controller;

import com.gestion.passengerservice.DTO.PassengerDTO;
import com.gestion.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public PassengerDTO createPassenger(@RequestBody PassengerDTO dto) {
        return passengerService.createPassenger(dto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public PassengerDTO getPassenger(@PathVariable Long id) {
        return passengerService.getPassenger(id);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('USER')")
    public List<PassengerDTO> getPassengersByUser(@PathVariable Long userId) {
        return passengerService.getPassengersByUser(userId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public PassengerDTO updatePassenger(@PathVariable Long id, @RequestBody PassengerDTO dto) {
        return passengerService.updatePassenger(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public void deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
    }
}
