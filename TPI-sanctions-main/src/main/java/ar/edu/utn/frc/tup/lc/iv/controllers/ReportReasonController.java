package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReportReasonDto;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.ReportReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las razones de reporte.
 */
@RestController
@RequestMapping("api/report-reason")
public class ReportReasonController {
    /**
     * Service for managing report reasons.
     */
    @Autowired
    private ReportReasonService reportReasonService;

    /**
     * Retrieves all report reasons.
     * @return A list of GetReportReasonDto objects.
     */
    @GetMapping("/all")
    public ResponseEntity<List<GetReportReasonDto>> getAllReasons() {
        List<GetReportReasonDto> reportReasons = reportReasonService.getAllReportReasons();

        return ResponseEntity.ok(reportReasons);
    }

    /**
     * Retrieves a report reason by its ID.
     * @param id The ID of the report reason.
     * @return The corresponding GetReportReasonDto object.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetReportReasonDto> getReasonById(@PathVariable("id") Integer id) {
        GetReportReasonDto reportReasons = reportReasonService.getReportReasonById(id);

        return ResponseEntity.ok(reportReasons);
    }

}
