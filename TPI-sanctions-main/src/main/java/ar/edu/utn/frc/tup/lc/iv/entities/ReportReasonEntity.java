package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
/**
 * Entity representing a report reason.
 */
@Data
@Audited
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "report_reasons")
public class ReportReasonEntity {

    /**
     * Unique identifier for the report reason.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Description of the report reason.
     */
    @Column(name = "report_reason", length = 1000)
    private String reportReason;

    /**
     * Base amount associated with the report reason.
     */
    @Column(name = "base_amount")
    private Double baseAmount;

    // Mandatory fields

    /**
     * Date and time when the report reason was created.
     */
    @Column(name = "created_datetime")
    private LocalDateTime createdDate;

    /**
     * User who created the report reason.
     */
    @Column(name = "created_user")
    private Integer createdUser;

    /**
     * Date and time when the report reason was last updated.
     */
    @Column(name = "last_updated_datetime")
    private LocalDateTime lastUpdatedDate;

    /**
     * User who last updated the report reason.
     */
    @Column(name = "last_updated_user")
    private Integer lastUpdatedUser;

}
