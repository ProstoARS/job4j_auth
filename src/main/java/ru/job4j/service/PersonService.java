package ru.job4j.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.domain.Person;
import ru.job4j.dto.PersonUpdateDTO;
import ru.job4j.dto.PersonUpdatePasswordDTO;
import ru.job4j.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;

    private final BCryptPasswordEncoder encoder;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    @Transactional
    public Optional<Person> update(PersonUpdateDTO personUpdateDTO) {
        Optional<Person> byId = personRepository.findById(personUpdateDTO.id());
        return byId.map(person -> {
            person.setLogin(personUpdateDTO.login());
            person.setPassword(this.encoder.encode(personUpdateDTO.password()));
            return person;
        });
    }

    @Transactional
    public boolean delete(int id) {
        return personRepository.findById(id)
                .map(entity -> {
                    personRepository.delete(entity);
                    personRepository.flush();
                    return true;
                }).orElse(false);
    }

    @Transactional
    public Person create(Person person) {
        person.setPassword(this.encoder.encode(person.getPassword()));
        return personRepository.save(person);
    }

    @Transactional
    public Optional<Person> changePassword(PersonUpdatePasswordDTO personDTO) {
        Optional<Person> personById = personRepository.findById(personDTO.id());
        return personById.map(person -> {
            person.setPassword(this.encoder.encode(personDTO.password()));
            return person;
        });
    }
}
