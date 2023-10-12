package ru.job4j.dto.example;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {

    private int id;

    private String country;

    private String city;

    private String street;

    private String house;

    /* Constructors, getters, setters */

}
