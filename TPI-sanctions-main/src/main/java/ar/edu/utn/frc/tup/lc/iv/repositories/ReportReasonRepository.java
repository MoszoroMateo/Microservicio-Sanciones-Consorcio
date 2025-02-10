package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.ReportReasonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Repository interface for managing ReportReasonEntity objects.
 */
@Repository
public interface ReportReasonRepository extends JpaRepository<ReportReasonEntity, Integer> {
    /**
     * Finds a ReportReasonEntity by its report reason.
     *
     * @param reportReason the reason of the report to search for
     * @return an Optional containing the found
     * ReportReasonEntity, or an empty Optional if not found
     */
    Optional<ReportReasonEntity> findReportReasonEntityByReportReason(String reportReason);
}
