package ar.edu.utn.frc.tup.lc.iv.dtos.post;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
/**
 * DTO for creating a new report.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostReportDto {

    /**
     * Identifier of the report reason.
     */
    @NotNull(message = "La razon no puede ser nula")
    private Integer reportReasonId;

    /**
     * Identifier of the associated plot.
     */
    @NotNull(message = "El lote al que va dirigido no puede ser nulo")
    private Integer plotId;

    /**
     * Description of the report.
     */
    @NotNull(message = "La descripcion no puede ser nula")
    @NotEmpty(message = "La descripcion no puede estar vacia")
    private String description;

    /**
     * List of complaint identifiers.
     */
    private List<Integer> complaints = new ArrayList<>();

    /**
     * Identifier of the user who created the report.
     */
    private Integer userId;

}
