package ru.job4j.exceprions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
@Slf4j
@RestControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Response> savePersonException(DataIntegrityViolationException exception) {
        Response response = new Response(HttpStatus.CONFLICT, exception.getMessage());
        log.error("Ошибка сохранения объекта", exception);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
