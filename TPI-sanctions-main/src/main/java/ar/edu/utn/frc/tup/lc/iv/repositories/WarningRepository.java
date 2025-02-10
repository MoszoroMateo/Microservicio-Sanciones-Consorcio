package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.WarningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Repository interface for managing WarningEntity objects.
 */
@Repository
public interface WarningRepository extends JpaRepository<WarningEntity, Integer> {
    /**
     * This method retrieves all warnings where the related report contains
     * the specified plot ID.
     *
     * @param plotId the ID of the plot to filter warnings by
     * @return a list of WarningEntity objects
     * that match the specified plot ID
     */
    List<WarningEntity> getWarningEntitiesByReportPlotId(Integer plotId);
}
