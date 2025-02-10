package ar.edu.utn.frc.tup.lc.iv.services.interfaces;

import ar.edu.utn.frc.tup.lc.iv.messaging.consumer.FinePaidDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetFineDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReducedFineDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetSanctionDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetWarningDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostFineDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostWarningDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutFineDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutFineStateDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
/**
 * Service interface for managing sanctions.
 */
@Service
public interface SanctionService {

    /**
     * Retrieves all reduced sanctions for a specific plot.
     *
     * @param plotId the ID of the plot
     * @return a list of reduced sanctions
     */
    List<GetSanctionDto> getAllReducedSanctions(Integer plotId);

    /**
     * Creates a new fine.
     *
     * @param fine the data transfer object containing the fine details
     * @return the created fine
     */
    GetFineDto createFine(PostFineDto fine);

    /**
     * Creates a new warning.
     *
     * @param warning the data transfer object
     *                containing the warning details
     * @return the created warning
     */
    GetWarningDto createWarning(PostWarningDto warning);

    /**
     * Retrieves a fine by its ID.
     *
     * @param id the ID of the fine
     * @return the fine with the specified ID
     */
    GetFineDto getById(Integer id);

    /**
     * Changes the state of an existing fine.
     *
     * @param fineDto the data transfer object containing
     *   the new state of the fine
     * @return the updated fine
     */
    GetFineDto changeFineState(PutFineStateDto fineDto);

    /**
     * Updates an existing fine.
     *
     * @param fineDto the data transfer object
     *    containing the updated fine details
     * @return the updated fine
     */
    GetFineDto updateFine(PutFineDto fineDto);

    /**
     * Retrieves reduced fines created between two dates.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return a list of reduced fines
     */
    List<GetReducedFineDto> getReducedFinesByCreatedDateBetweenDates(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Retrieves all fines.
     *
     * @return a list of all fines
     */
    List<GetFineDto> getAllFines();

    /**
     * Retrieves all fine states.
     *
     * @return a map of fine states
     */
    Map<String, String> getAllFineStates();

    /**
     * Changes the state of a fine to "PAID".
     *
     * @param dto the DTO containing the fine data
     */
    void changeFineToPaid(FinePaidDto dto);

}
