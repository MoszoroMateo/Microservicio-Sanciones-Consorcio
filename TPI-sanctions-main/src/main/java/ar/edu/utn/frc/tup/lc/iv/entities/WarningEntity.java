package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
/**
 * Entity representing a warning.
 */
@Data
@Audited
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "warnings")
public class WarningEntity {

    /**
     * Unique identifier for the warning.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The report associated with this warning.
     */
    @ManyToOne
    @JoinColumn(name = "report_id", referencedColumnName = "id")
    private ReportEntity report;

    /**
     * Indicates whether the warning is active.
     */
    @Column
    private boolean active;

    // Mandatory fields

    /**
     * Date and time when the warning was created.
     */
    @Column(name = "created_datetime")
    private LocalDateTime createdDate;

    /**
     * User who created the warning.
     */
    @Column(name = "created_user")
    private Integer createdUser;

    /**
     * Date and time when the warning was last updated.
     */
    @Column(name = "last_updated_datetime")
    private LocalDateTime lastUpdatedDate;

    /**
     * User who last updated the warning.
     */
    @Column(name = "last_updated_user")
    private Integer lastUpdatedUser;

}
