package ar.edu.utn.frc.tup.lc.iv.dtos.put;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * DTO for updating the reason of a report.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PutReportReasonDto {

    /**
     * Report reason identifier.
     * Cannot be null.
     */
    @NotNull(message = "El id del motivo de informe no puede ser nulo")
    private Integer id;

    /**
     * Reason for the report.
     * Cannot be null and must be between 5 and 50 characters.
     */
    @NotNull(message = "La razon no puede ser nula")
    @Size(min = 5, max = 50, message = "La razon debe contener entre 5 y 50 caracteres maximo")
    private String reportReason;

    /**
     * Base amount for the report.
     * Must be between 0 and 999,999.
     */
    @Min(value = 0, message = "La cantidad no puede ser menor a 0")
    @Max(value = 999999, message = "La cantidad no puede ser mayor a $999.999") //ToDo: Checkear monto maximo y minimo posible
    private Double baseAmount;

    /**
     * User identifier.
     * Cannot be null.
     */
    @NotNull(message = "El usuario no puede ser nulo")
    private Integer userId;

}
