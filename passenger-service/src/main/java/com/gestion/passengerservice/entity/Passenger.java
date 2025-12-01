package com.gestion.passengerservice.entity;



import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;        // prénom
    private String lastName;         // nom
    private String email;
    private String passportNumber;   // numéro de passeport
    private LocalDate birthDate;     // date de naissance
}
