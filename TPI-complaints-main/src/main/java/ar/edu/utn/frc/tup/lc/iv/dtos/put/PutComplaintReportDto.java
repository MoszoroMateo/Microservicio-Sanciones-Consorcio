package ar.edu.utn.frc.tup.lc.iv.dtos.put;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Clase DTO utilizada para actualizar la información de un informe de denuncias.
 * Contiene los datos necesarios para asociar o modificar
 * un informe con un conjunto de denuncias.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PutComplaintReportDto {

     /**
     * Identificador único del informe de denuncias que se desea actualizar.
     * Este campo es obligatorio.
     */
    @NotNull(message = "El id del informe no puede ser nulo")
    private Integer reportId;

    /**
     * Identificador único del usuario que realiza la actualización.
     * Este campo es obligatorio.
     */
    @NotNull(message = "El id de usuario no puede ser nulo")
    private Integer userId;

    /**
     * Lista de identificadores de las denuncias asociadas al informe.
     * Este campo es obligatorio y no puede estar vacío.
     */
    @NotNull(message = "El listado de informes no puede ser nulo")
    private List<Integer> complaintIds;
}
