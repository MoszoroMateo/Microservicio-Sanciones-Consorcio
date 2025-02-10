package ar.edu.utn.frc.tup.lc.iv.dtos.put;

import ar.edu.utn.frc.tup.lc.iv.entities.enums.FineState;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * DTO for updating the state of a fine.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PutFineStateDto {

    /**
     * Fine identifier.
     */
    @NotNull(message = "El id de la multa no puede ser nulo")
    private Integer id;

    /**
     * New state of the fine.
     */
    @NotNull(message = "El nuevo estado no puede ser nulo")
    private FineState fineState;

    /**
     * Reason for the state change.
     */
    @NotNull(message = "La razon del cambio de estado no puede ser nula")
    @Size(min = 3, max = 50, message = "El motivo de cambio tiene que tener entre 3 y 50 caracteres maximo")
    private String stateReason;

    /**
     * User identifier.
     */
    @NotNull(message = "El id de usuario no puede ser nulo")
    private Integer userId;
}
