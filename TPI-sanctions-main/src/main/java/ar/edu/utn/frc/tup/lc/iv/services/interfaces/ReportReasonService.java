package ar.edu.utn.frc.tup.lc.iv.services.interfaces;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReportReasonDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostReportReasonDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutReportReasonDto;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Service interface for managing report reasons.
 */
@Service
public interface ReportReasonService {

    /**
     * Retrieves all report reasons.
     *
     * @return a list of all report reasons
     */
    List<GetReportReasonDto> getAllReportReasons();

    /**
     * Retrieves a report reason by its ID.
     *
     * @param id the ID of the report reason
     * @return the report reason with the specified ID
     */
    GetReportReasonDto getReportReasonById(Integer id);

    /**
     * Adds a new report reason.
     *
     * @param reportReasonDto the data transfer object
     * containing the report reason details
     * @return the added report reason
     */
    GetReportReasonDto addReportReason(PostReportReasonDto reportReasonDto);

    /**
     * Updates an existing report reason.
     *
     * @param reportReasonDto the data transfer object containing
     *  the updated report reason details
     * @return the updated report reason
     */
    GetReportReasonDto updateReportReason(PutReportReasonDto reportReasonDto);
}
