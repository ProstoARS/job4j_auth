package ru.job4j.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.domain.Person;
import ru.job4j.dto.PersonUpdateDTO;
import ru.job4j.dto.PersonUpdatePasswordDTO;
import ru.job4j.exceprions.BusinessException;
import ru.job4j.exceprions.Response;
import ru.job4j.service.PersonService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response> handleException(BusinessException e) {
        Response response = new Response(HttpStatus.OK, e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/testExceptionHandler", produces = APPLICATION_JSON_VALUE)
    public void testExceptionHandler(@RequestParam(required = false, defaultValue = "false") boolean exception)
            throws BusinessException {
        if (exception) {
            throw new BusinessException("BusinessException in testExceptionHandler");
        }
    }

    @GetMapping("/all")
    public List<Person> findAll() {
        return this.personService.findAll();
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable int id) {
        var person = this.personService.findById(id);
        if (person.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Объект не был найден!");
        }
        return person.get();
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Person created", person.getLogin())
                .contentType(MediaType.APPLICATION_JSON)
                .body(personService.create(person));
    }

    @PutMapping("/update")
    public Person update(@RequestBody PersonUpdateDTO updateDTO) {
        return personService.update(updateDTO).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Объект не удалось обновить!")
        );
    }

    @PatchMapping
    public Person updatePassword(@RequestBody PersonUpdatePasswordDTO personDTO) {
        return personService.changePassword(personDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found!"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (!personService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Объект не удален!");
        }
        return ResponseEntity.ok().build();
    }
}
