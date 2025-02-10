package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para representar información detallada de una denuncia.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetComplaintDto {

    /**
     * Identificador único de la denuncia.
     */
    private Integer id;

    /**
     * ID del usuario que creó la denuncia.
     */
    private Integer userId;

    /**
     * ID del reporte asociado a la denuncia.
     */
    private Integer reportId;

    /**
     * Motivo principal de la denuncia.
     */
    private String complaintReason;

    /**
     * Otro motivo de la denuncia, si existe.
     */
    private String anotherReason;

    /**
     * Estado actual de la denuncia.
     */
    private String complaintState;

    /**
     * Descripción de la denuncia.
     */
    private String description;

    /**
     * Estado de la denuncia.
     */
    private String stateReason;

    /**
     * Fecha y hora donde se creó la denuncia.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    /**
     * ID del usuario que creó el registro.
     */
    private Integer createdUser;

    /**
     * Fecha y hora donde se actualizó la denuncia por última vez.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdatedDate;

    /**
     * ID del usuario que actualizó la denuncia por última vez.
     */
    private Integer lastUpdatedUser;
}
