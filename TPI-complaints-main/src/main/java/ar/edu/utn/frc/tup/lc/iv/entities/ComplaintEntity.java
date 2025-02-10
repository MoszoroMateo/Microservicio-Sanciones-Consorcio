package ar.edu.utn.frc.tup.lc.iv.entities;

import ar.edu.utn.frc.tup.lc.iv.entities.enums.ComplaintState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad que representa una denuncia en la base de datos.
 * Esta clase mapea la tabla "COMPLAINTS" en la base de datos
 * y contiene los detalles
 * completos de una denuncia, incluyendo información
 * sobre el estado, el motivo y la descripción de la denuncia.
 */
@Audited
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "complaints")
public class ComplaintEntity {

    /**
     * Identificador único de la denuncia.
     * Es la clave primaria de la entidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Identificador único del usuario que presenta la denuncia.
     * Este campo no puede ser nulo.
     */
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    /**
     * Identificador único del informe al que pertenece la denuncia.
     * Este campo no puede ser nulo.
     */
    @Column(name = "report_id")
    private Integer reportId;

    /**
     * Motivo de la denuncia.
     * Este campo no puede ser nulo.
     */
    @Column(name = "complaint_reason", nullable = false)
    private String complaintReason;

    /**
     * Descripción adicional del motivo de la denuncia,
     * utilizada cuando el motivo es "Otro".
     * Este campo puede ser nulo.
     */
    @Column(name = "another_reason", nullable = true)
    private String anotherReason;

    /**
     * Estado actual de la denuncia.
     * Este campo no puede ser nulo y debe ser uno
     * de los valores definidos en el enumerado ComplaintState.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "complaint_state", nullable = false)
    private ComplaintState complaintState;

    /**
     * Descripción detallada de la denuncia.
     * Este campo no puede ser nulo y debe tener
     * un máximo de 1000 caracteres.
     */
    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    /**
     * Razón del estado actual de la denuncia.
     * Este campo no puede ser nulo y tiene una longitud máxima de 1000 caracteres.
     */
    @Column(name = "state_reason", nullable = false, length = 1000)
    private String stateReason;

    /**
     * Lista de imágenes asociadas a la denuncia.
     * Relación uno a muchos con la entidad PictureEntity.
     */
    @OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL)
    private List<PictureEntity> pictures;

    /**
     * Fecha y hora en que fue creada la denuncia.
     * Este campo no puede ser nulo.
     */
    @Column(name = "created_datetime", nullable = false)
    private LocalDateTime createdDate;

    /**
     * Identificador del usuario que creó la denuncia.
     * Este campo no puede ser nulo.
     */
    @Column(name = "created_user", nullable = false)
    private Integer createdUser;

    /**
     * Fecha y hora de la última actualización de la denuncia.
     * Este campo no puede ser nulo.
     */
    @Column(name = "last_updated_datetime", nullable = false)
    private LocalDateTime lastUpdatedDate;

    /**
     * Identificador del usuario que realizó la última actualización.
     * Este campo no puede ser nulo.
     */
    @Column(name = "last_updated_user", nullable = false)
    private Integer lastUpdatedUser;
}
