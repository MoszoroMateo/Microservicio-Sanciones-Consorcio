package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
/**
 * Entity representing a disclaimer associated with a fine.
 */
@Data
@Audited
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "disclaimers")
public class DisclaimerEntity {
    /**
     * Unique identifier for the disclaimer.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The fine associated with this disclaimer.
     */
    @OneToOne
    @JoinColumn(name = "fine_id", referencedColumnName = "id") // Foreign Key to FineEntity
    private FineEntity fine;

    /**
     * The text of the disclaimer.
     */
    @Column(length = 1000)
    private String disclaimer;
    // Mandatory fields
    /**
     * The date and time when the disclaimer was created.
     */
    @Column(name = "created_datetime")
    private LocalDateTime createdDate;

    /**
     * The user who created the disclaimer.
     */
    @Column(name = "created_user")
    private Integer createdUser;

    /**
     * The date and time when the disclaimer was last updated.
     */
    @Column(name = "last_updated_datetime")
    private LocalDateTime lastUpdatedDate;

    /**
     * The user who last updated the disclaimer.
     */
    @Column(name = "last_updated_user")
    private Integer lastUpdatedUser;

}
