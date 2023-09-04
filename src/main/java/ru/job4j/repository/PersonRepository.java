package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.domain.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
