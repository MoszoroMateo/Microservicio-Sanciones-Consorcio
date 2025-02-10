package ar.edu.utn.frc.tup.lc.iv.dtos.put;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * This DTO is sent to the Complaints microservice to
 * attach complaints to a report.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PutComplaintReportDto {
    /**
     * Report identifier.
     */
    @NotNull(message = "El id del informe no puede ser nulo")
    private Integer reportId;
    /**
     * User identifier.
     */
    @NotNull(message = "El id de usuario no puede ser nulo")
    private Integer userId;
    /**
     * List of complaint identifiers.
     */
    @NotNull(message = "El listado de informes no puede ser nulo")
    private List<Integer> complaintIds;
}
