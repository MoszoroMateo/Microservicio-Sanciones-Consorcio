package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Representa una queja reducida utilizada para mostrar información básica
 * en la lista de "Consultar Denuncias".
 */
@Data
public class GetReducedComplaintDto {

    /**
     * Identificador único de la denuncia.
     */
    private Integer Id;

    /**
     * Identificador del usuario que presentó la denuncia.
     */
    private Integer userId;

    /**
     * Identificador del reporte asociado a la denuncia.
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
     * Fecha y hora de creación de la queja.
     * Formato: yyyy-MM-dd HH:mm:ss.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    /**
     * Fecha y hora de la última actualización de la queja.
     * Formato: yyyy-MM-dd HH:mm:ss.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdatedDate;

    /**
     * Cantidad de archivos asociados a la queja.
     */
    private Integer fileQuantity;

}
