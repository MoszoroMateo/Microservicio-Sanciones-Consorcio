package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReportReasonDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostReportReasonDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutReportReasonDto;
import ar.edu.utn.frc.tup.lc.iv.entities.ReportReasonEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.ReportReasonRepository;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.ReportReasonService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * Service implementation for managing report reasons.
 */
@Service
@NoArgsConstructor
public class ReportReasonServiceImpl implements ReportReasonService {
    /** Repository for accessing report reason data. */
    @Autowired
    private ReportReasonRepository reportReasonRepository;

    /** Mapper for converting between entities and DTOs. */
    @Autowired
    private ModelMapper modelMapper;


    /**
     * Retrieves all report reasons with their details.
     * @return a list of all report reasons as DTOs
     * @throws EntityNotFoundException if no
     * report reasons are found
     */
    @Override
    public List<GetReportReasonDto> getAllReportReasons() {
        List<ReportReasonEntity> reasonEntities = reportReasonRepository.findAll();

        if (reasonEntities.isEmpty()) {
            throw new EntityNotFoundException("No se encontro ningun motivo de informe");
        }

        List<GetReportReasonDto> reasonDtos = new ArrayList<>();

        reasonEntities.forEach(entity -> reasonDtos.add(modelMapper.map(entity, GetReportReasonDto.class)));

        return reasonDtos;
    }


    /**
     * Retrieves the details of a specific report reason by its ID.
     * @param id the ID of the report reason
     * @return the report reason as a DTO
     * @throws EntityNotFoundException if the report reason is not found
     */
    @Override
    public GetReportReasonDto getReportReasonById(Integer id) {
        ReportReasonEntity reasonEntity = reportReasonRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("No se encontró el motivo de informe con el id: " + id)
        );


        return modelMapper.map(reasonEntity, GetReportReasonDto.class);
    }


    /**
     * Adds a new report reason.
     *
     * @param postReportReasonDto the DTO containing the report reason data
     * @return the created report reason as a DTO
     * @throws EntityExistsException if the report reason already exists
     */
    @Override
    public GetReportReasonDto addReportReason(PostReportReasonDto postReportReasonDto) {
        ReportReasonEntity existingReason = reportReasonRepository
                .findReportReasonEntityByReportReason(postReportReasonDto.getReportReason())
                .orElse(null);

        if (existingReason != null) {
            throw new EntityExistsException("El motivo de informe ya existe");
        }

        ReportReasonEntity reasonEntity = new ReportReasonEntity();

        //Mappeo los valores del dto al entity a mano
        reasonEntity.setReportReason(postReportReasonDto.getReportReason());
        reasonEntity.setBaseAmount(postReportReasonDto.getBaseAmount());
        reasonEntity.setCreatedDate(LocalDateTime.now());
        reasonEntity.setCreatedUser(postReportReasonDto.getUserId());
        reasonEntity.setLastUpdatedDate(LocalDateTime.now());
        reasonEntity.setLastUpdatedUser(postReportReasonDto.getUserId());

        reportReasonRepository.save(reasonEntity);

        return modelMapper.map(reasonEntity, GetReportReasonDto.class);
    }


    /**
     * Updates a specific report reason.
     *
     * @param putReportReasonDto the DTO containing the updated report reason data
     * @return the updated report reason as a DTO
     * @throws EntityNotFoundException if the report reason is not found
     */
    @Override
    public GetReportReasonDto updateReportReason(PutReportReasonDto putReportReasonDto) {
        ReportReasonEntity reportReasonEntity = reportReasonRepository.findById(putReportReasonDto.getId()).orElseThrow(() ->
                new EntityNotFoundException("No se encontró el motivo de informe con el id: " + putReportReasonDto.getId())
        );

        reportReasonEntity.setReportReason(putReportReasonDto.getReportReason());
        reportReasonEntity.setBaseAmount(putReportReasonDto.getBaseAmount());
        reportReasonEntity.setLastUpdatedDate(LocalDateTime.now());
        reportReasonEntity.setLastUpdatedUser(putReportReasonDto.getUserId());

        reportReasonRepository.save(reportReasonEntity);


        return modelMapper.map(reportReasonEntity, GetReportReasonDto.class);
    }

}
