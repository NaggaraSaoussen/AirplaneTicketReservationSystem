package com.gestion.flightservice.Controller;

import com.gestion.flightservice.DTO.FlightDTO;
import com.gestion.flightservice.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService service;

    @GetMapping
    public List<FlightDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public FlightDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public FlightDTO create(@RequestBody FlightDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public FlightDTO update(@PathVariable Long id, @RequestBody FlightDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

