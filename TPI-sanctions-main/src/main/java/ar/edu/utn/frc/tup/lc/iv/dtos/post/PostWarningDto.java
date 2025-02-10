package ar.edu.utn.frc.tup.lc.iv.dtos.post;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * DTO para crear una nueva advertencia.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostWarningDto {

    /**
     * Identifier of the report.
     */
    @NotNull(message = "El reporte no puede ser nulo")
    private int reportId;

    /**
     * Identifier of the user who created the warning.
     */
    @NotNull(message = "El usuario no puede ser nulo")
    private Integer createdUser;

}
