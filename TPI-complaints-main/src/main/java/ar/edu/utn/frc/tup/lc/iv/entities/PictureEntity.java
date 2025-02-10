package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

/**
 * Entidad que representa una imagen asociada a una denuncia.
 * Esta clase mapea la tabla "PICTURES" en la base de datos y contiene
 * los detalles relacionados con las imágenes,
 * como la URL y la información de creación y actualización.
 */
@Audited
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pictures")
public class PictureEntity {

    /**
     * Identificador único de la imagen.
     * Es la clave primaria de la entidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Relación muchos a uno con la entidad ComplaintEntity.
     * Representa la denuncia a la que pertenece la imagen.
     */
    @ManyToOne
    @JoinColumn(name = "complaint_id", referencedColumnName = "id")
    private ComplaintEntity complaint;

    /**
     * URL de la imagen almacenada.
     * Este campo no puede ser nulo.
     */
    @Column(name = "picture_url", nullable = false)
    private String pictureUrl;

    /**
     * Fecha y hora en que la imagen fue creada.
     * Este campo no puede ser nulo.
     */
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    /**
     * Identificador del usuario que creó la imagen.
     * Este campo no puede ser nulo.
     */
    @Column(name = "created_user", nullable = false)
    private Integer createdUser;

    /**
     * Fecha y hora de la última actualización de la imagen.
     * Este campo no puede ser nulo.
     */
    @Column(name = "last_updated_date", nullable = false)
    private LocalDateTime lastUpdatedDate;

    /**
     * Identificador del usuario que realizó la última actualización.
     * Este campo no puede ser nulo.
     */
    @Column(name = "last_updated_user", nullable = false)
    private Integer lastUpdatedUser;

}
