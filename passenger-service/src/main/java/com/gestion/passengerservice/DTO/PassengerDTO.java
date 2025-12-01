package com.gestion.passengerservice.DTO;

import lombok.Data;

import java.time.LocalDate;
@Data
public class PassengerDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String passportNumber;

}
