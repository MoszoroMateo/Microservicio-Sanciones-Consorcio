package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReducedReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutReportStateDto;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for managing reports.
 */
@RestController
@RequestMapping("api/report")
public class ReportController {
    /**
     * Service for managing reports.
     */
    @Autowired
    private ReportService reportService;
    /**
     * Creates a new report based on the provided data.
     *
     * @param postReportDto The DTO containing
     *the data for the new report.
     * @return ResponseEntity<GetReportDto>
     *containing the created report details.
     */
    @PostMapping("")
    public ResponseEntity<GetReportDto> postReport(@RequestBody PostReportDto postReportDto) {
        GetReportDto report = reportService.postReport(postReportDto);

        return ResponseEntity.ok(report);
    }
    /**
     * Updates the state of an existing report.
     *
     * @param putReportStateDto The DTO containing
     * the updated state details for the report.
     * @return ResponseEntity<GetReportDto> containing the updated report details.
     */
    @PutMapping("/state")
    public ResponseEntity<GetReportDto> updateReportState(@RequestBody PutReportStateDto putReportStateDto) {
        GetReportDto report = reportService.putReportState(putReportStateDto);

        return ResponseEntity.ok(report);
    }
    /**
     * Retrieves a report by its ID.
     *
     * @param id The ID of the report to be retrieved.
     * @return ResponseEntity<GetReportDto>
     *     containing the report details.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetReportDto> getReportById(@PathVariable("id") Integer id) {
        GetReportDto report = reportService.getById(id);

        return ResponseEntity.ok(report);
    }
    /**
     * Retrieves all available states for reports.
     *
     * @return ResponseEntity<Map<String, String>>
     * containing a map of report states.
     */
    @GetMapping("/states")
    public ResponseEntity<Map<String, String>> getReportStates() {
        Map<String, String> reportStates = reportService.getAllReportStates();

        return ResponseEntity.ok(reportStates);
    }
    /**
     * Retrieves a list of all reports in a reduced format.
     *
     * @return ResponseEntity<List<GetReducedReportDto>>
     * containing the list of reports,
     *or a bad request response if no reports are found.
     */

    @GetMapping("/all")
    public ResponseEntity<List<GetReducedReportDto>> getReports() {
        List<GetReducedReportDto> reports = reportService.getAllReducedReports();
        if (reports != null) {
            return ResponseEntity.ok(reports);
        }
        return ResponseEntity.badRequest().build();

    }
 /**
 * Updates an existing report with the provided data.
 * @param putReportDto The DTO with updated report details.
 * @return ResponseEntity<GetReportDto> with the updated
  * report or a bad request response if the update fails.
 */
    @PutMapping("")
    public ResponseEntity<GetReportDto> putReport(@RequestBody PutReportDto putReportDto) {

        GetReportDto report = reportService.putReport(putReportDto);

        if (report != null) {
            return ResponseEntity.ok(report);
        }

        return ResponseEntity.badRequest().build();
    }
}
