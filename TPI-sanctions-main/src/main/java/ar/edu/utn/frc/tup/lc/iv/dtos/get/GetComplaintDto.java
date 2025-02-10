package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for Complaint information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetComplaintDto {
    /**
     * Complaint identifier.
     */
    private Integer id;
    /**
     * Identifier of the user who made the complaint.
     */
    private Integer userId;
    /**
     * Identifier of the associated report.
     */
    private Integer reportId;
    /**
     * Reason for the complaint.
     */
    private String complaintReason;
    /**
     * Additional reason for the complaint.
     */
    private String anotherReason;
    /**
     * State of the complaint.
     */
    private String complaintState;
    /**
     * Description of the complaint.
     */
    private String description;
    /**
     * Reason for the state of the complaint.
     */
    private String stateReason;
    /**
     * Date when the complaint was created.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    /**
     * User who created the complaint.
     */
    private Integer createdUser;
    /**
     * Date when the complaint was last updated.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdatedDate;
    /**
     * User who last updated the complaint.
     */
    private Integer lastUpdatedUser;
}
