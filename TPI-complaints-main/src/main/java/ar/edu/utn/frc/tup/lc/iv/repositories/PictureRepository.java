package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.PictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para manejar entidades de tipo {@link PictureEntity}.
 */
@Repository
public interface PictureRepository extends JpaRepository<PictureEntity, Integer> {

}
