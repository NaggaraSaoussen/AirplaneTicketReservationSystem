package com.gestion.passengerservice.controller;

import com.gestion.passengerservice.DTO.PassengerDTO;
import com.gestion.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService service;

    @GetMapping
    public List<PassengerDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public PassengerDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public PassengerDTO create(@RequestBody PassengerDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public PassengerDTO update(@PathVariable Long id, @RequestBody PassengerDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}