package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.clients.ComplaintClient;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetComplaintDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReducedReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutComplaintReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutReportStateDto;
import ar.edu.utn.frc.tup.lc.iv.entities.ReportEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.ReportReasonEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.enums.ReportState;
import ar.edu.utn.frc.tup.lc.iv.repositories.ReportReasonRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.ReportRepository;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.ReportService;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
/**
 * Service implementation for managing reports.
 */
@Service
@NoArgsConstructor
public class ReportServiceImpl implements ReportService {
    /** Repository for accessing report data. */
    @Autowired
    private ReportRepository reportRepository;
    /** Repository for accessing report reason data. */
    @Autowired
    private ReportReasonRepository reportReasonRepository;
    /** Client for interacting with the complaint microservice. */
    @Autowired
    private ComplaintClient complaintClient;
    /** Mapper for converting between entities and DTOs. */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Creates a new report.
     *
     * @param postReportDto the DTO containing the report data
     * @return the created report as a DTO
     * @throws EntityNotFoundException if the report reason is not found
     */
    @Override
    @Transactional
    public GetReportDto postReport(PostReportDto postReportDto) {

        ReportReasonEntity reportReasonEntity = reportReasonRepository.findById(postReportDto.getReportReasonId()).orElseThrow(() ->
                new EntityNotFoundException("No se encontró el motivo de informe con el id: " + postReportDto.getReportReasonId())
        );

        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setReportState(ReportState.PENDING);
        reportEntity.setStateReason("Nuevo informe");
        reportEntity.setReportReason(reportReasonEntity);
        reportEntity.setPlotId(postReportDto.getPlotId());
        reportEntity.setDescription(postReportDto.getDescription());
        reportEntity.setCreatedDate(LocalDateTime.now());
        reportEntity.setCreatedUser(postReportDto.getUserId());
        reportEntity.setLastUpdatedDate(LocalDateTime.now());
        reportEntity.setLastUpdatedUser(postReportDto.getUserId());

        reportRepository.save(reportEntity);

        GetReportDto getReportDto = modelMapper.map(reportEntity, GetReportDto.class);

        if (!postReportDto.getComplaints().isEmpty()) {
            PutComplaintReportDto putComplaintReportDto = new PutComplaintReportDto();
            putComplaintReportDto.setReportId(reportEntity.getId());
            putComplaintReportDto.setComplaintIds(postReportDto.getComplaints());
            putComplaintReportDto.setUserId(postReportDto.getUserId());
            List<GetComplaintDto> complaintDtos = complaintClient.appendComplaint(putComplaintReportDto);
            getReportDto.setComplaints(complaintDtos);
        }
        return getReportDto;
    }

    /**
     * Updates the state of a report.
     *
     * @param putReportStateDto the DTO containing the updated report state data
     * @return the updated report as a DTO
     */
    @Override
    public GetReportDto putReportState(PutReportStateDto putReportStateDto) {

        ReportEntity reportEntity = reportRepository.findById(putReportStateDto.getId()).orElse(null);

        if (reportEntity == null) {
            return null;
        }

        reportEntity.setReportState(putReportStateDto.getReportState());
        reportEntity.setStateReason(putReportStateDto.getStateReason());
        reportEntity.setLastUpdatedUser(putReportStateDto.getUserId());
        reportEntity.setLastUpdatedDate(LocalDateTime.now());

        reportRepository.save(reportEntity);

        GetReportDto getReportDto = modelMapper.map(reportEntity, GetReportDto.class);

        //Consulta al micro de denuncias para traer el listado
        getReportDto.setComplaints(complaintClient.getComplaintsByReportId(getReportDto.getId()));

        return getReportDto;
    }

    /**
     * Retrieves the details of a specific report by its ID.
     *
     * @param id the ID of the report
     * @return the report as a DTO
     */
    @Override
    public GetReportDto getById(Integer id) {

        ReportEntity reportEntity = reportRepository.findById(id).orElse(null);

        if (reportEntity == null) {
            return null;
        }

        GetReportDto getReportDto = modelMapper.map(reportEntity, GetReportDto.class);

        //Consulta al micro de denuncias para traer el listado
        getReportDto.setComplaints(complaintClient.getComplaintsByReportId(getReportDto.getId()));

        return getReportDto;
    }

    /**
     * Retrieves all report states.
     *
     * @return a map of all report states
     */
    @Override
    public Map<String, String> getAllReportStates() {
        return ReportState.toMap();
    }

    /**
     * Retrieves all reports with reduced details.
     *
     * @return a list of all reduced reports as DTOs
     */
    @Override
    public List<GetReducedReportDto> getAllReducedReports() {
        List<ReportEntity> reportEntities = reportRepository.findAll();

        if (reportEntities.isEmpty()) {
            Collections.emptyList();
        }

        List<GetReducedReportDto> reportDtos = new ArrayList<>();
        for (ReportEntity reportEntity : reportEntities) {
            reportDtos.add(modelMapper.map(reportEntity, GetReducedReportDto.class));
        }
        return reportDtos;
    }

    /**
     * Updates a specific report.
     *
     * @param putReportDto the DTO containing the updated report data
     * @return the updated report as a DTO
     * @throws EntityNotFoundException if the report is not found
     */
    @Override
    @Transactional
    public GetReportDto putReport(PutReportDto putReportDto) {
        ReportEntity reportEntity = reportRepository.findById(putReportDto.getId()).orElseThrow(() ->
            new EntityNotFoundException("No se encontro el informe con id: " + putReportDto.getId())
        );

        //Update del entity
        reportEntity.setDescription(putReportDto.getDescription());
        reportEntity.setLastUpdatedUser(putReportDto.getUserId());
        reportEntity.setLastUpdatedDate(LocalDateTime.now());

        reportRepository.save(reportEntity);
        GetReportDto getreportDto = modelMapper.map(reportEntity, GetReportDto.class);

        //update de las denuncias anexadas
        if (!putReportDto.getComplaintsIds().isEmpty()) {
            PutComplaintReportDto putComplaintReportDto = new PutComplaintReportDto(
                putReportDto.getId(), putReportDto.getUserId(), putReportDto.getComplaintsIds()
            );

            getreportDto.setComplaints(complaintClient.appendComplaint(putComplaintReportDto));
        }
        return getreportDto;
    }
}
