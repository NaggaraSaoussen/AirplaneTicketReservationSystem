package com.gestion.flightservice.Controller;

import com.gestion.flightservice.DTO.FlightDTO;
import com.gestion.flightservice.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService service;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    public List<FlightDTO> getAll() {
        return service.getAll();
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public FlightDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody FlightDTO dto) {
        FlightDTO createdFlight = service.create(dto); // 🔹 récupérer le résultat
        return ResponseEntity.ok(
                Map.of(
                        "message", "Flight created successfully",
                        "flight", createdFlight
                )
        );
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody FlightDTO dto) {
        FlightDTO updatedFlight = service.update(id, dto);

        // Retourner un message + les données mises à jour
        return ResponseEntity.ok(
                Map.of(
                        "message", "Flight updated successfully",
                        "flight", updatedFlight
                )
        );
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.ok(
                Map.of("message", "Flight deleted successfully")
        );
    }

}
