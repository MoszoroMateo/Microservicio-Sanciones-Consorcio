package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.ComplaintEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para manejar entidades de tipo {@link ComplaintEntity}.
 */
@Repository
public interface ComplaintRepository extends JpaRepository<ComplaintEntity, Integer> {
     /**
     * Encuentra denuncias por el id del informe.
     */
    List<ComplaintEntity> findAllByReportId(Integer reportId);
}
