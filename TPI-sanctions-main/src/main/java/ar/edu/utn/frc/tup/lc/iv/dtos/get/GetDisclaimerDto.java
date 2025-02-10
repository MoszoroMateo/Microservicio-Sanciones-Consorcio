package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for Disclaimer information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDisclaimerDto {
    /**
     * Disclaimer identifier.
     */
    private Integer id;
    /**
     * Identifier of the associated fine.
     */
    private Integer fineId;

    /**
     * Disclaimer text.
     */
    private String disclaimer;
    /**
     * Date when the disclaimer was created.
     */
    private LocalDateTime createdDate;
    /**
     * User who created the disclaimer.
     */
    private Integer createdUser;
    /**
     * Date when the disclaimer was last updated.
     */
    private LocalDateTime lastUpdatedDate;
    /**
     * User who last updated the disclaimer.
     */
    private Integer lastUpdatedUser;
}
