package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repository interface for managing ReportEntity objects.
 */
public interface ReportRepository extends JpaRepository<ReportEntity, Integer> {

}
