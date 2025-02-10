package ar.edu.utn.frc.tup.lc.iv.messaging.consumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO for fine paid messages.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinePaidDto {

    /**
     * Initial date.
     */
    private String dateFrom;

    /**
     * Finish date.
     */
    private String dateTo;

    /**
     * ID of owner.
     */
    private int ownerId;
    /**
     * ID of boleta.
     */
    private int boletaId;
    /**
     * Status.
     */
    private String status;
    /**
     * ID of fine.
     */
    private int fineId;
    /**
     * Constructor.
     * @param i ID of fine.
     * @param now Finish date.
     * @param i1 ID of owner.
     */
    public FinePaidDto(int i, LocalDate now, int i1) {
        this.fineId = i;
        this.dateTo = now.toString();
        this.ownerId = i1;
    }
}
