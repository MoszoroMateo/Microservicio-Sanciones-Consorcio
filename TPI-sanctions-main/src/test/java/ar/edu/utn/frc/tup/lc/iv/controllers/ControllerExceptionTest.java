package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.ErrorApi;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ControllerExceptionTest {
    private ControllerException controllerException;

    @BeforeEach
    void setUp() {
        controllerException = new ControllerException();
    }

    @Test
    void handleGenericError_shouldReturnInternalServerError() {
        Exception exception = new Exception("Generic error");
        ResponseEntity<ErrorApi> response = controllerException.handleError(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Generic error", response.getBody().getMessage());
    }

    @Test
    void handleIllegalArgumentError_shouldReturnBadRequest() {
        IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");
        ResponseEntity<ErrorApi> response = controllerException.handleError(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid argument", response.getBody().getMessage());
    }

    @Test
    void handleEntityNotFoundError_shouldReturnNotFound() {
        EntityNotFoundException exception = new EntityNotFoundException("Entity not found");
        ResponseEntity<ErrorApi> response = controllerException.handleError(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Entity not found", response.getBody().getMessage());
    }

    @Test
    void handleEntityExistsError_shouldReturnConflict() {
        EntityExistsException exception = new EntityExistsException("Entity already exists");
        ResponseEntity<ErrorApi> response = controllerException.handleError(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Entity already exists", response.getBody().getMessage());
    }

    @Test
    void handleResponseStatusError_shouldReturnCustomStatus() {
        ResponseStatusException exception = new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        ResponseEntity<ErrorApi> response = controllerException.handleError(exception);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Access denied", response.getBody().getMessage());
    }

    @Test
    void handleIllegalStateError_shouldReturnBadRequest() {
        IllegalStateException exception = new IllegalStateException("Illegal state");
        ResponseEntity<ErrorApi> response = controllerException.handleError(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Illegal state", response.getBody().getMessage());
    }

}