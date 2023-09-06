package ru.job4j.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.domain.Person;
import ru.job4j.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    @Transactional
    public boolean update(Person person) {
        boolean result = false;
        try {
            personRepository.saveAndFlush(person);
            result = true;
        } catch (Exception e) {
             log.error("Ошибка обновления", e);
        }
        return result;
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
        return personRepository.save(person);
    }

}
