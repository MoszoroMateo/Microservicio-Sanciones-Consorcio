package ar.edu.utn.frc.tup.lc.iv.dtos.put;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * DTO for updating report information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PutReportDto {

    /**
     * Report identifier.
     */
    private Integer id;

    /**
     * User identifier.
     */
    private Integer userId;

    /**
     * Description of the report.
     */
    private String description;

    /**
     * List of complaint identifiers associated with the report.
     */
    private List<Integer> complaintsIds;
}
