package ar.edu.utn.frc.tup.lc.iv.dtos.post;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase DTO utilizada para crear una nueva denuncia.
 * Incluye información requerida como el usuario, el motivo de la denuncia,
 * descripción y archivos adjuntos.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostComplaintDto {

    /**
     * Identificador único del usuario que crea la denuncia.
     * Este campo es obligatorio.
     */
    @NotNull(message = "El id de usuario no puede ser nulo")
    private Integer userId;

    /**
     * Razón principal de la denuncia.
     * Este campo es obligatorio.
     */
    @NotNull(message = "El tipo de denuncia no puede ser nulo")
    private String complaintReason;

    /**
     * Razón adicional si el motivo principal es "Otro".
     * Este campo es opcional.
     */
    private String anotherReason;

    /**
     * Descripción detallada de la denuncia.
     * Este campo es obligatorio y tiene un tamaño máximo de 800 caracteres.
     */
    @NotNull(message = "La descripcion no puede ser nula")
    @Size(min = 1, max = 800, message = "La descripcion debe tener entre 1 y 500 caracteres maximo")
    private String description;

    /**
     * Lista de imágenes o archivos adjuntos relacionados con la denuncia.
     * Este campo es opcional y puede estar vacío.
     */
    private List<MultipartFile> pictures = new ArrayList<>();
}
