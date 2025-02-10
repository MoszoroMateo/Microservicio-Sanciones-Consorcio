package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetFineDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReducedFineDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetSanctionDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetWarningDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostFineDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostWarningDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutFineDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutFineStateDto;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.SanctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
/**
 * REST controller for managing sanctions.
 */
@RestController
@RequestMapping("api/sanction")
public class SanctionController {
    /**
     * Service for managing sanctions.
     */
    @Autowired
    private SanctionService sanctionService;

  /**
 * Retrieves all current sanctions.
 * Optionally filters by plot ID (Integer).
 * @param plotId Optional plot ID.
 * @return ResponseEntity with a list of sanctions.
 */
    @GetMapping("/all")
    public ResponseEntity<List<GetSanctionDto>> getAllReducedSanctions(@RequestParam(value = "plotId", required = false) Integer plotId) {
        List<GetSanctionDto> sanctions = sanctionService.getAllReducedSanctions(plotId);
        if (sanctions != null) {
            return ResponseEntity.ok(sanctions);
        }
        return ResponseEntity.badRequest().build();
    }
    /**
     * Creates a new fine.
     *
     * @param fine The DTO containing the fine details.
     * @return ResponseEntity containing the
     * created fine details.
     */
    @PostMapping("/fine")
    public ResponseEntity<GetFineDto> postFine(@RequestBody PostFineDto fine){
        return ResponseEntity.ok(sanctionService.createFine(fine));
    }
    /**
     * Retrieves a fine by its ID.
     *
     * @param id The ID of the fine to be retrieved.
     * @return ResponseEntity containing the fine details.
     */
    @GetMapping("/fine/{id}")
    public ResponseEntity<GetFineDto> getFineById(@PathVariable Integer id){
        return ResponseEntity.ok(sanctionService.getById(id));
    }
    /**
     * Creates a new warning.
     *
     * @param warning The DTO containing the
     * warning details.
     * @return ResponseEntity containing the
     * created warning details.
     */
    @PostMapping("/warning")
    public ResponseEntity<GetWarningDto> postWarning(@RequestBody PostWarningDto warning){
        return ResponseEntity.ok(sanctionService.createWarning(warning));
    }
    /**
     * Updates the state of an existing fine.
     *
     * @param fine The DTO containing the updated fine state details.
     * @return ResponseEntity containing the updated fine
     * details, or a bad request response if the update fails.
     */
    @PutMapping("/changeStateFine")
    public ResponseEntity<GetFineDto> putFine(@RequestBody PutFineStateDto fine){
        GetFineDto response = sanctionService.changeFineState(fine);
        if (response != null){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().build();
    }
    /**
     * Updates an existing fine.
     *
     * @param fineDto The DTO containing the updated fine details.
     * @return ResponseEntity containing the updated fine details.
     */
    @PutMapping("/updateFine")
    public ResponseEntity<GetFineDto> updateFine(@RequestBody PutFineDto fineDto) {
        return ResponseEntity.ok(sanctionService.updateFine(fineDto));
    }
    /**
     * Retrieves reduced complaints between specified dates.
     *
     * @param startDate The start date for filtering complaints.
     * @param endDate The end date for filtering complaints.
     * @return ResponseEntity containing a list of reduced complaints.
     */
    @GetMapping("/fines")
    public ResponseEntity<List<GetReducedFineDto>> getReducedComplaintsBetweenDates(
            @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<GetReducedFineDto> complaints = sanctionService.getReducedFinesByCreatedDateBetweenDates(startDateTime, endDateTime);

        return ResponseEntity.ok(complaints);
    }
    /**
     * Retrieves all fines.
     *
     * @return ResponseEntity containing a list of all fines,
     * or a not found response if no fines are found.
     */
    @GetMapping("/allFines")
    public ResponseEntity<List<GetFineDto>> getAllFines() {
        List<GetFineDto> response = sanctionService.getAllFines();
        if (response.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
    /**
     * Retrieves all fine states.
     *
     * @return ResponseEntity containing a map of fine states,
     * or a not found response if no states are found.
     */
    @GetMapping("/allFinesState")
    public ResponseEntity<Map<String,String>> getAllFinesState() {
        Map<String,String> response = sanctionService.getAllFineStates();
        if (response.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

}
