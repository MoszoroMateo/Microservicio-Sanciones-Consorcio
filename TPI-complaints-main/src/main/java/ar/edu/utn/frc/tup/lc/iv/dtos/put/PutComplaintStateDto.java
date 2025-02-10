package ar.edu.utn.frc.tup.lc.iv.dtos.put;

import ar.edu.utn.frc.tup.lc.iv.entities.enums.ComplaintState;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase DTO utilizada para actualizar el estado de una denuncia.
 * Contiene los datos necesarios para realizar
 * el cambio de estado de una denuncia.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PutComplaintStateDto {

    /**
     * Identificador único de la denuncia que se desea actualizar.
     * Este campo es obligatorio.
     */
    @NotNull(message = "El id de la denuncia no puede ser nula")
    private Integer id;

    /**
     * Identificador único del usuario que realiza la actualización.
     * Este campo es obligatorio.
     */
    @NotNull(message = "El id de usuario no puede ser nulo")
    private Integer userId;

    /**
     * Nuevo estado de la denuncia.
     * Este campo es obligatorio.
     */
    @NotNull(message = "El nuevo estado no puede ser nulo")
    private ComplaintState complaintState;

    /**
     * Motivo por el cual se realiza el cambio de estado.
     * Este campo es obligatorio y debe tener entre 3 y 50 caracteres.
     */
    @NotNull(message = "La razon del cambio de estado no puede ser nula")
    @Size(min = 3, max = 50, message = "El motivo de cambio tiene que tener entre 3 y 50 caracteres maximo")
    private String stateReason;
}
