package com.vzincoder.api.controller;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.vzincoder.api.dto.MessageResponseDTO;
import com.vzincoder.api.exception.DataIntegrityException;
import com.vzincoder.api.exception.EntityNotFoundException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({ EntityNotFoundException.class })
    public ResponseEntity<MessageResponseDTO> handleEntityNotFoundException(RuntimeException ex, WebRequest request) {
        MessageResponseDTO responseDTO = new MessageResponseDTO(ex.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ DataIntegrityException.class })
    public ResponseEntity<MessageResponseDTO> handleDataIntegrityException(RuntimeException ex, WebRequest request) {
        MessageResponseDTO responseDTO = new MessageResponseDTO(ex.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> "Field '" + fieldError.getField() + "': " + fieldError.getDefaultMessage())
                .collect(Collectors.joining("; "));
        MessageResponseDTO responseDTO = new MessageResponseDTO("Validation Error: " + errorMessage);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MessageResponseDTO> handleValidationExceptions(HttpMessageNotReadableException ex) {
        String errorMessage = "A valid JSON payload is required. Please ensure that your request contains all the required fields and follows the correct JSON format.";
        MessageResponseDTO responseDTO = new MessageResponseDTO(errorMessage);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
    

}
