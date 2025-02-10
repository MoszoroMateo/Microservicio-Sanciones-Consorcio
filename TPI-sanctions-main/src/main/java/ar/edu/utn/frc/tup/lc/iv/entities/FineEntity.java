package ar.edu.utn.frc.tup.lc.iv.entities;

import ar.edu.utn.frc.tup.lc.iv.entities.enums.FineState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 * Entity representing a fine.
 */
@Data
@Audited
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fines")
public class FineEntity {

    /**
     * Unique identifier for the fine.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The state of the fine.
     */
    @Column(name = "fine_state")
    private FineState fineState;

    /**
     * Reason for the current state of the fine.
     */
    @Column(name = "state_reason", length = 1000)
    private String stateReason;

    /**
     * The report associated with this fine.
     */
    @ManyToOne
    @JoinColumn(name = "report_id", referencedColumnName = "id")
    private ReportEntity report;

    /**
     * Date when the fine is issued for collection.
     */
    @Column(name = "discharge_date")
    private LocalDate dischargeDate;

    /**
     * Amount of the fine.
     */
    @Column
    private Double amount;

    // Mandatory fields

    /**
     * Date and time when the fine was created.
     */
    @Column(name = "created_datetime")
    private LocalDateTime createdDate;

    /**
     * User who created the fine.
     */
    @Column(name = "created_user")
    private Integer createdUser;

    /**
     * Date and time when the fine was last updated.
     */
    @Column(name = "last_updated_datetime")
    private LocalDateTime lastUpdatedDate;

    /**
     * User who last updated the fine.
     */
    @Column(name = "last_updated_user")
    private Integer lastUpdatedUser;

}
