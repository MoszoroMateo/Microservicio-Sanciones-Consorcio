package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Reduced Fine information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetReducedFineDto {
    /**
     * Fine identifier.
     */
    private Integer fine_id;
    /**
     * Plot identifier.
     */
    private Integer plot_id;

    /**
     * Description of the fine.
     */
    private String description;
    /**
     * Amount of the fine.
     */
    private Double amount;
}
