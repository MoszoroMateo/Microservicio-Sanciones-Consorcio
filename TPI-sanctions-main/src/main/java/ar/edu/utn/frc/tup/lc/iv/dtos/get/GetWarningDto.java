package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import ar.edu.utn.frc.tup.lc.iv.entities.ReportEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/**
 * DTO for warning information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetWarningDto {

    /**
     * Warning identifier.
     */
    private Integer id;

    /**
     * Associated report entity.
     */
    private ReportEntity report;

    /**
     * Indicates if the warning is active.
     */
    private boolean active;

    /**
     * Date when the warning was created.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    /**
     * Identifier of the user who created the warning.
     */
    private Integer createdUser;

    /**
     * Date when the warning was last updated.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdatedDate;

    /**
     * Identifier of the user who last updated the warning.
     */
    private Integer lastUpdatedUser;
}
