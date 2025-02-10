package ar.edu.utn.frc.tup.lc.iv.dtos.post;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * DTO for creating a new report reason.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostReportReasonDto {

    /**
     * Reason for the report.
     */
    @NotNull(message = "La razon no puede ser nula")
    @Size(min = 5, max = 50, message = "La razon debe contener entre 5 y 50 caracteres maximo")
    private String reportReason;

    /**
     * Base amount for the report reason.
     */
    @Min(value = 0, message = "La cantidad no puede ser menor a 0")
    @Max(value = 999999, message = "La cantidad no puede ser mayor a $999.999") //ToDo: Checkear monto maximo y minimo posible
    private Double baseAmount;

    /**
     * Identifier of the user who created the report reason.
     */
    @NotNull(message = "El usuario no puede ser nulo")
    private Integer userId;
}
