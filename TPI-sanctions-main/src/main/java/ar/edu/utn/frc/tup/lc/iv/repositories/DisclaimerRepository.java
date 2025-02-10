package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.DisclaimerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repository interface for managing DisclaimerEntity objects.
 */
@Repository
public interface DisclaimerRepository extends JpaRepository<DisclaimerEntity, Integer> {

    /**
     * Checks if a DisclaimerEntity exists by the given fine ID.
     *
     * @param fineId the ID of the fine to check for
     * @return true if a DisclaimerEntity
     * exists with the given fine ID, false otherwise
     */
    boolean existsByFineId(Integer fineId);

    /**
     * Finds a DisclaimerEntity by the given fine ID.

     * @param fineId the ID of the fine to search for
     * @return the DisclaimerEntity associated
     * with the given fine ID
     */
    DisclaimerEntity findByFineId(Integer fineId);

}
