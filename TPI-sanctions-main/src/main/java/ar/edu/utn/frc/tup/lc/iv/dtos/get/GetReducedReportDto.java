package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for Reduced Report information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetReducedReportDto {
    /**
     * Report identifier.
     */
    private Integer id;
    /**
     * State of the report.
     */
    private String reportState;
    /**
     * Identifier of the associated plot.
     */
    private Integer plotId;
    /**
     * Description of the report.
     */
    private String description;
    /**
     * Date when the report was created.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
}
