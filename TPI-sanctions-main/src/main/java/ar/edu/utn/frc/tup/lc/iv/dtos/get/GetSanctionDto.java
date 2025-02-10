package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/**
 * DTO for Sanction information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetSanctionDto {

    /**
     * Sanction identifier.
     */
    private Integer id;

    /**
     * State of the fine.
     */
    private String fineState;

    /**
     * Identifier of the associated plot.
     */
    private Integer plotId;

    /**
     * Description of the sanction.
     */
    private String description;

    /**
     * Amount of the fine.
     */
    private Double amount;

    /**
     * Date when the sanction was created.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    /**
     * Indicates if a disclaimer has been submitted.
     */
    private Boolean hasSubmittedDisclaimer;

    /**
     * Identifier of the associated report.
     */
    private Integer reportId;

}
