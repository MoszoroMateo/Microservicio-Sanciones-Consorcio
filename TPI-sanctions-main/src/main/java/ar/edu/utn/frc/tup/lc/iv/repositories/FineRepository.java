package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.FineEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.enums.FineState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
/**
 * Repository interface for managing FineEntity objects.
 */
@Repository
public interface FineRepository extends JpaRepository<FineEntity, Integer> {
    /**
     * This method retrieves all fines where the related report contains
     * the specified plot ID.
     * @param plotId the ID of the plot to filter fines by
     * @return a list of FineEntity objects that match the specified plot ID
     */
    List<FineEntity> getFineEntitiesByReportPlotId(Integer plotId);
    /**
     * This method retrieves all fines with the specified fine state.
     *
     * @param state the state of the fines to filter by
     * @return a list of FineEntity objects that match the specified fine state
     */
    List<FineEntity> getFineEntitiesByFineState(FineState state);
/**
 * Retrieves fines created between the specified dates.
 * @param startDate the start date
 * @param endDate the end date
 * @return a list of fines created between the dates
 */
    List<FineEntity> getFineEntitiesByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
