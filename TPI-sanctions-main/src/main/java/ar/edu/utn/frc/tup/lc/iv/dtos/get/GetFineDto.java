package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import ar.edu.utn.frc.tup.lc.iv.entities.ReportEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 * DTO for Fines information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFineDto {
    /**
     * Fine identifier.
     */
    private Integer id;

    /**
     * State of the fine.
     */
    private String fineState;
    /**
     * Reason for the state of the fine.
     */
    private String stateReason;
    /**
     * Report associated with the fine.
     */
    private ReportEntity report;
    /**
     * Disclaimer.
     */
    private String disclaimer;

    /**
     * Fine discharge date.
     */
    private LocalDate dischargeDate;
    /**
     * Fine amount.
     */
    private Double amount;
    /**
     * Fine creation date.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    /**
     * User who created the fine.
     */
    private Integer createdUser;

    /**
     * Last update date of the fine.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdatedDate;
    /**
     * User who made the last update to the fine.
     */
    private Integer lastUpdatedUser;
}
