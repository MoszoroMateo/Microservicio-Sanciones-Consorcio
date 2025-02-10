package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.ErrorApi;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
/**
 * Global exception handler for the application.
 * This class handles different types of exceptions and returns
 * appropriate error responses to the client.
 */
@ControllerAdvice
@NoArgsConstructor
public class ControllerException {

    /**
     * Handles generic exceptions (Exception class)
     * and returns a 500 Internal Server Error response.
     * @param e The exception that was thrown.
     * @return The error response entity.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorApi> handleError(Exception e) {
        ErrorApi error = buildError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /**
     * Handles IllegalArgumentException
     * and returns a 400 Bad Request response.
     * @param e The IllegalArgumentException that was thrown.
     * @return The error response entity.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorApi> handleError(IllegalArgumentException e) {
        ErrorApi error = buildError(e.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Handles EntityNotFoundException and returns a 404 Not Found response.
     * @param e The EntityNotFoundException that was thrown.
     * @return The error response entity.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorApi> handleError(EntityNotFoundException e) {
        ErrorApi error = buildError(e.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Handles EntityExistsException and returns a 409 Conflict response.
     * @param e The EntityExistsException that was thrown.
     * @return The error response entity.
     */
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorApi> handleError(EntityExistsException e) {
        ErrorApi error = buildError(e.getMessage(), HttpStatus.CONFLICT);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /**
     * Handles ResponseStatusException and returns
     * the appropriate HTTP error response.
     * @param e The ResponseStatusException that was thrown.
     * @return The error response entity.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorApi> handleError(ResponseStatusException e) {
        ErrorApi error = buildError(e.getReason(), HttpStatus.valueOf(e.getStatusCode().value()));
        return ResponseEntity.status(e.getStatusCode()).body(error);
    }


    /**
     * Handles IllegalStateException and returns a 400 Bad Request response.
     * @param e The IllegalStateException that was thrown.
     * @return The error response entity.
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorApi> handleError(IllegalStateException e) {
        ErrorApi error = buildError(e.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }



    /**
     * Builds an ErrorApi object with the provided message and HTTP status.
     * @param message The error message.
     * @param status The HTTP status.
     * @return The ErrorApi object.
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
