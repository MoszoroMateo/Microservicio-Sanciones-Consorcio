package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetDisclaimerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostDisclaimerDto;
import ar.edu.utn.frc.tup.lc.iv.entities.DisclaimerEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.FineEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.enums.FineState;
import ar.edu.utn.frc.tup.lc.iv.repositories.DisclaimerRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.FineRepository;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.DisclaimerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

/**
 * Service implementation for managing disclaimers.
 */
@Service
@NoArgsConstructor
public class DisclaimerServiceImpl implements DisclaimerService {
    /** Repository for accessing disclaimer data. */

    @Autowired
    private DisclaimerRepository disclaimerRepository;
    /** Repository for accessing fine data. */
    @Autowired
    private FineRepository fineRepository;

    /** Mapper for converting between entities and DTOs. */
    @Qualifier("modelMapper")
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public  GetDisclaimerDto postDisclimer(PostDisclaimerDto disclaimerDto) {
        /**
         * Creates a new disclaimer and updates the related fine state.
         *
         * @param disclaimerDto the DTO containing the disclaimer data
         * @return the created disclaimer as a DTO
         * @throws EntityNotFoundException if the related fine is not found
         * @throws RuntimeException if an error occurs while saving the disclaimer
         */
        FineEntity fineEntity = fineRepository.findById(disclaimerDto.getFineId()).orElseThrow(() ->
                new EntityNotFoundException("No se encontró una multa relacionada al id: " + disclaimerDto.getFineId())
        );
        DisclaimerEntity disclaimerEntity = new DisclaimerEntity();
        try {

            disclaimerEntity.setDisclaimer(disclaimerDto.getDisclaimer());
            disclaimerEntity.setFine(fineEntity);
            disclaimerEntity.setCreatedUser(disclaimerDto.getUserId());
            disclaimerEntity.setCreatedDate(LocalDateTime.now());
            disclaimerEntity.setLastUpdatedDate(LocalDateTime.now());
            disclaimerEntity.setLastUpdatedUser(disclaimerDto.getUserId());
            disclaimerEntity = disclaimerRepository.save(disclaimerEntity);

        } catch (Exception e) {
            throw new IllegalStateException("Se produjo un error al intentar guardar el descargo: " + e.getMessage(), e);
        }

        GetDisclaimerDto disclaimerResponse = modelMapper.map(disclaimerEntity, GetDisclaimerDto.class);
        disclaimerResponse.setFineId(disclaimerEntity.getFine().getId());

        putFineState(fineEntity, disclaimerEntity.getCreatedUser());

        return disclaimerResponse;
    }

    /**
     * Updates the state of a fine to "APPEALED".
     *
     * @param fineEntity the fine entity to update
     * @param userId the ID of the user performing the update
     * @throws EntityNotFoundException if the fine is not found
     */
    private void putFineState(FineEntity fineEntity, Integer userId) {

        FineEntity fine = fineRepository.findById(fineEntity.getId()).orElseThrow(() ->
                new EntityNotFoundException("No se encontró una multa relacionada al id: " + fineEntity.getId())
        );
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        fine.setFineState(FineState.APPEALED);
        fine.setStateReason(fine.getStateReason()+"\n"+ sdf.format(new Date())+" - "+ "La multa ha sido apelada");
        fine.setLastUpdatedUser(userId);
        fine.setLastUpdatedDate(LocalDateTime.now());

        fineRepository.save(fine);
    }


}
