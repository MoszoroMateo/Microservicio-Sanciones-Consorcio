package ar.edu.utn.frc.tup.lc.iv.dtos.put;

import ar.edu.utn.frc.tup.lc.iv.entities.enums.ReportState;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * DTO used exclusively for updating the state of a report.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PutReportStateDto {
    /**
     * Report identifier.
     * Cannot be null.
     */
    @NotNull(message = "El id del informe no puede ser nulo")
    private Integer id;
    /**
     * New state of the report.
     * Cannot be null.
     */
    @NotNull(message = "El nuevo estado no puede ser nulo")
    private ReportState reportState;
    /**
     * Reason for the state change.
     * Cannot be null and must be between 3 and 50 characters.
     */
    @NotNull(message = "La razon del cambio de estado no puede ser nula")
    @Size(min = 3, max = 50, message = "El motivo de cambio tiene que tener entre 3 y 50 caracteres maximo")
    private String stateReason;
    /**
     * User identifier.
     * Cannot be null.
     */
    @NotNull(message = "El id de usuario no puede ser nulo")
    private Integer userId;

}
