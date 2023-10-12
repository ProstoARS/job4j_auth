package ru.job4j.dto.example;

import lombok.Data;

@Data
public class User {

    private int id;

    private String name;

    private String surname;

    private Address address;

    /* Constructors, getters, setters */

}