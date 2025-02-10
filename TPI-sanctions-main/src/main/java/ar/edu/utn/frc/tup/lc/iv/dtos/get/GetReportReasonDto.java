package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * DTO for report reason information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetReportReasonDto {

    /**
     * Reason identifier.
     */
    private Integer id;

    /**
     * Description of the report reason.
     */
    private String reportReason;

    /**
     * Base amount associated with the report reason.
     */
    private Double baseAmount;

}
