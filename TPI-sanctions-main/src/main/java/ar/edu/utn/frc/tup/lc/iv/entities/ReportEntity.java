package ar.edu.utn.frc.tup.lc.iv.entities;

import ar.edu.utn.frc.tup.lc.iv.entities.enums.ReportState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
/**
 * Entity representing a report.
 */
@Data
@Audited
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reports")
public class ReportEntity {

    /**
     * Unique identifier for the report.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The state of the report.
     */
    @Column(name = "report_state", nullable = false)
    private ReportState reportState;

    /**
     * Reason for the current state of the report.
     */
    @Column(name = "state_reason", length = 1000)
    private String stateReason;

    /**
     * The reason associated with this report.
     */
    @ManyToOne
    @JoinColumn(name = "report_reason_id", referencedColumnName = "id", nullable = false)
    private ReportReasonEntity reportReason;

    /**
     * Identifier for the plot associated with this report.
     */
    @Column(name = "plot_id", nullable = false)
    private Integer plotId;

    /**
     * Description of the report.
     */
    @Column(length = 1000)
    private String description;

    // Mandatory fields

    /**
     * Date and time when the report was created.
     */
    @Column(name = "created_datetime")
    private LocalDateTime createdDate;

    /**
     * User who created the report.
     */
    @Column(name = "created_user")
    private Integer createdUser;

    /**
     * Date and time when the report was last updated.
     */
    @Column(name = "last_updated_datetime")
    private LocalDateTime lastUpdatedDate;

    /**
     * User who last updated the report.
     */
    @Column(name = "last_updated_user")
    private Integer lastUpdatedUser;

}
