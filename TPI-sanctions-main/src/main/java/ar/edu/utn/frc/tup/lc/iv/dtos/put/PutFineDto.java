package ar.edu.utn.frc.tup.lc.iv.dtos.put;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * DTO for updating fine information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PutFineDto {
    /**
     * Fine identifier.
     */
    @NotNull(message = "El id de la multa no puede ser nulo")
    private Integer id;

    /**
     * Amount of the fine.
     */
    private Double amount;

    /**
     * User identifier.
     */
    @NotNull(message = "El usuario no puede ser nulo")
    private Integer userId;
}
