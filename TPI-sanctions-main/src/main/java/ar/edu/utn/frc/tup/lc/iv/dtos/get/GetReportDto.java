package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


/**
 * DTO for Report information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetReportDto {

    /**
     * Report identifier.
     */
    private Integer id;

    /**
     * State of the report.
     */
    private String reportState;

    /**
     * Reason for the state of the report.
     */
    private String stateReason;

    /**
     * Reason associated with the report.
     */
    private GetReportReasonDto reportReason;

    /**
     * Identifier of the associated plot.
     */
    private Integer plotId;

    /**
     * Description of the report.
     */
    private String description;

    /**
     * List of complaints associated with the report.
     */
    private List<GetComplaintDto> complaints;

    /**
     * Date when the report was created.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    /**
     * User who created the report.
     */
    private Integer createdUser;

    /**
     * Last update date of the report.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdatedDate;

    /**
     * User who made the last update to the report.
     */
    private Integer lastUpdatedUser;
}
