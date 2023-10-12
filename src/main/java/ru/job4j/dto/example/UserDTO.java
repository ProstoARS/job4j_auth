package ru.job4j.dto.example;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

    private String name;

    private String surname;

    private int addressId;

    /* Constructors, getters, setters */
}
