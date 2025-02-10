package ar.edu.utn.frc.tup.lc.iv.dtos.post;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Data Transfer Object for posting a fine.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostFineDto {

    /**
     * The ID of the report.
     * Cannot be null.
     */
    @NotNull(message = "El reporte no puede ser nulo")
    private int reportId;

    /**
     * The amount of the fine.
     * Cannot be null.
     */
    @NotNull(message = "El monto no puede ser nulo")
    private double amount;

    /**
     * The ID of the user who created the fine.
     * Cannot be null.
     */
    @NotNull(message = "El usuario no puede ser nulo")
    private Integer createdUser;

}
