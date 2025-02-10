package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.ErrorApi;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

/**
 * Maneja excepciones en los controladores.
 */
@ControllerAdvice
public class ControllerException {

    // Manejo de errores generico
    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<ErrorApi> handleError(Exception e) {
    // ErrorApi error = buildError(e.getMessage(),
    // HttpStatus.INTERNAL_SERVER_ERROR);
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    // }

    /**
     * Maneja errores de argumentos inválidos.
     * @param e Excepción capturada.
     * @return Respuesta con código 400 (BAD REQUEST).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorApi> handleError(IllegalArgumentException e) {
        ErrorApi error = buildError(e.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Maneja errores cuando no se encuentra una entidad.
     * @param e Excepción capturada.
     * @return Respuesta con código 404 (NOT FOUND).
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorApi> handleError(EntityNotFoundException e) {
        ErrorApi error = buildError(e.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Maneja errores de entidades duplicadas.
     * @param e Excepción capturada.
     * @return Respuesta con código 409 (CONFLICT).
     */
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorApi> handleError(EntityExistsException e) {
        ErrorApi error = buildError(e.getMessage(), HttpStatus.CONFLICT);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // Manejo de errores http
    // @ExceptionHandler(ResponseStatusException.class)
    // public ResponseEntity<ErrorApi> handleError(ResponseStatusException e) {
    // ErrorApi error = buildError(e.getReason(),
    // HttpStatus.valueOf(e.getStatusCode().value()));
    // return ResponseEntity.status(e.getStatusCode()).body(error);
    // }

    /**
     * Crea un objeto ErrorApi con los detalles del error.
     * @param message Mensaje del error.
     * @param status  Código HTTP del error.
     * @return Objeto ErrorApi.
     */
    private ErrorApi buildError(String message, HttpStatus status) {
        return ErrorApi.builder()
                .timestamp(String.valueOf(Timestamp.from(ZonedDateTime.now().toInstant())))
                .error(status.getReasonPhrase())
                .status(status.value())
                .message(message)
                .build();
    }
}
