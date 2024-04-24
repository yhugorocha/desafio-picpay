package com.picpaysimplificado.configs;

import com.picpaysimplificado.dtos.ExceptionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity threatDataIntegrity(DataIntegrityViolationException data){
        ExceptionDTO exceptionDTO = new ExceptionDTO("Already registered user", "400");
        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity threatEntityNotFound(EntityNotFoundException entity){
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity threatGeneralRuntime(RuntimeException runtime){
        ExceptionDTO exceptionDTO = new ExceptionDTO(runtime.getMessage(), "500");
        return ResponseEntity.internalServerError().body(exceptionDTO);
    }
}
