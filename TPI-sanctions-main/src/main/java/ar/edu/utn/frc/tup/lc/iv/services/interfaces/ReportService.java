package ar.edu.utn.frc.tup.lc.iv.services.interfaces;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReducedReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutReportStateDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
/**
 * Service interface for managing reports.
 */
@Service
public interface ReportService {

    /**
     * Creates a new report.
     *
     * @param postReportDto the data transfer
     * object containing the report details
     * @return the created report
     */
    GetReportDto postReport(PostReportDto postReportDto);

    /**
     * Updates the state of an existing report.
     *
     * @param putReportStateDto the data transfer
     * object containing the new state of the report
     * @return the updated report
     */
    GetReportDto putReportState(PutReportStateDto putReportStateDto);

    /**
     * Retrieves a report by its ID.
     *
     * @param id the ID of the report
     * @return the report with the specified ID
     */
    GetReportDto getById(Integer id);

    /**
     * Retrieves all report states.
     *
     * @return a map of report states
     */
    Map<String, String> getAllReportStates();

    /**
     * Updates an existing report.
     *
     * @param putReportDto the data transfer object
     *   containing the updated report details
     * @return the updated report
     */
    GetReportDto putReport(PutReportDto putReportDto);

    /**
     * Retrieves all reduced reports.
     *
     * @return a list of reduced reports
     */
    List<GetReducedReportDto> getAllReducedReports();

}
