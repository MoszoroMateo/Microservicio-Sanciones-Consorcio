package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.messaging.consumer.FinePaidDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetFineDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReducedFineDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetSanctionDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetWarningDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostFineDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostWarningDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutFineDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutFineStateDto;
import ar.edu.utn.frc.tup.lc.iv.entities.DisclaimerEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.FineEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.ReportEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.WarningEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.enums.FineState;
import ar.edu.utn.frc.tup.lc.iv.entities.enums.ReportState;
import ar.edu.utn.frc.tup.lc.iv.repositories.DisclaimerRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.FineRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.ReportRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.WarningRepository;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.SanctionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Service implementation for managing sanctions.
 */
@Service
@EnableScheduling
@NoArgsConstructor
public class SanctionServiceImpl implements SanctionService {
    /** Repository for accessing fine data. */
    @Autowired
    private FineRepository fineRepository;

    /** Repository for accessing warning data. */
    @Autowired
    private WarningRepository warningRepository;

    /** Repository for accessing report data. */
    @Autowired
    private ReportRepository reportRepository;

    /** Mapper for converting between entities and DTOs. */
    @Autowired
    private ModelMapper modelMapper;

    /** Repository for accessing disclaimer data. */
    @Autowired
    private DisclaimerRepository disclaimerRepository;

    /** Date format for the state reason. */
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /** Separator for Strings. */
    private static final String SEPARATOR = " - ";

    /**
     * Retrieves all reduced sanctions
     * for a specific plot or all plots.
     * @param plotId the ID of the plot (optional)
     * @return a list of reduced sanctions as DTOs
     */
    @Override
    public List<GetSanctionDto> getAllReducedSanctions(Integer plotId) {
        List<FineEntity> fines;
        List<WarningEntity> warnings;
        if(plotId == null){
            fines = fineRepository.findAll();
            warnings = warningRepository.findAll();
        } else {
            fines = fineRepository.getFineEntitiesByReportPlotId(plotId);
            warnings = warningRepository.getWarningEntitiesByReportPlotId(plotId);

        }


        if (fines.isEmpty() && warnings.isEmpty()) {
            Collections.emptyList();
        }

        List<GetSanctionDto> getSanctionDtos = new ArrayList<>();
        for(FineEntity fine : fines){
            GetSanctionDto offense = modelMapper.map(fine, GetSanctionDto.class);
            offense.setPlotId(fine.getReport().getPlotId());
            offense.setReportId(fine.getReport().getId());
            offense.setDescription(fine.getReport().getDescription());
            getSanctionDtos.add(offense);
            //Si existe un descargo en esa multa no podra subir otro
            if(disclaimerRepository.existsByFineId(fine.getId())){
                offense.setHasSubmittedDisclaimer(true);
            } else { //Sino, podra subir un descargo y no tendra descargo asociado
                offense.setHasSubmittedDisclaimer(false);
            }


        }
        for(WarningEntity warning : warnings){
            GetSanctionDto offense = modelMapper.map(warning, GetSanctionDto.class);
            offense.setPlotId(warning.getReport().getPlotId());
            offense.setReportId(warning.getReport().getId());
            offense.setDescription(warning.getReport().getDescription());
            getSanctionDtos.add(offense);
            offense.setHasSubmittedDisclaimer(false);
        }
        return getSanctionDtos;
    }
    /**
     * Creates a new fine.
     * @param fineDto the DTO containing the fine data
     * @return the created fine as a DTO
     * @throws EntityNotFoundException if the related
     * report is not found
     */
    @Override
    public GetFineDto createFine(PostFineDto fineDto) {
        GetFineDto fineResponse = null;
        // Buscamos el repositorio relacionado a la multa
        ReportEntity report = reportRepository.findById(fineDto.getReportId()).orElseThrow(() ->
            new EntityNotFoundException("No se encontró un reporte relacionado al id: " + fineDto.getReportId())
        );
        if(report != null){
            SimpleDateFormat sdfA = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
            // Creamos y definimos la multa
            FineEntity fine = new FineEntity();
            fine.setReport(report);
            fine.setAmount(fineDto.getAmount());
            fine.setCreatedUser(fineDto.getCreatedUser());
            fine.setFineState(FineState.PENDING);
            fine.setStateReason(sdfA.format(new Date())+ SEPARATOR + "Se crea la multa - "+"\n");
            fine.setCreatedDate(LocalDateTime.now());
            fine.setLastUpdatedDate(LocalDateTime.now());
            fine.setLastUpdatedUser(fineDto.getCreatedUser());

            report.setReportState(ReportState.CLOSED);
            report.setStateReason(report.getStateReason() +"\n"+ "Se Genero una multa apartir de este reporte");
            report.setLastUpdatedDate(LocalDateTime.now());
            report.setLastUpdatedUser(fineDto.getCreatedUser());
            try{
                FineEntity savedFine = fineRepository.save(fine);
                reportRepository.save(report);
                fineResponse = modelMapper.map(savedFine, GetFineDto.class);
            } catch (Exception e){
                throw new IllegalStateException("Se produjo un error al intentar guardar la multa: " + e.getMessage(), e);
            }


        }
        return fineResponse;
    }
    /**
     * Creates a new warning.
     * @param warningDto the DTO containing the warning data
     * @return the created warning as a DTO
     * @throws EntityNotFoundException
     * if the related report is not found
     */
    @Override
    public GetWarningDto createWarning(PostWarningDto warningDto) {
        GetWarningDto warningResponse = null;
        ReportEntity report = reportRepository.findById(warningDto.getReportId()).orElseThrow(() ->
                new EntityNotFoundException("No se encontró un reporte relacionado al id: " + warningDto.getReportId())
        );
        if(report != null){
            // Creamos y definimos la advertencia
            WarningEntity warning = new WarningEntity();
            warning.setReport(report);
            warning.setCreatedUser(warningDto.getCreatedUser());
            warning.setActive(true);
            warning.setCreatedDate(LocalDateTime.now());
            warning.setLastUpdatedDate(LocalDateTime.now());
            warning.setLastUpdatedUser(warningDto.getCreatedUser());
            try{
                WarningEntity savedWarning = warningRepository.save(warning);
                warningResponse = modelMapper.map(savedWarning, GetWarningDto.class);
            } catch (Exception e){
                throw new IllegalStateException("Se produjo un error al intentar guardar la multa: " + e.getMessage(), e);
            }

        }
        return warningResponse;
    }
    /**
     * Changes the state of a fine.
     * @param fineDto the DTO containing the updated fine state data
     * @return the updated fine as a DTO
     * @throws EntityNotFoundException if the fine is not found
     * @throws IllegalStateException
     * if the state transition is not allowed
     */
    @Override
    public GetFineDto changeFineState(PutFineStateDto fineDto){
        FineEntity fine = fineRepository.findById(fineDto.getId()).orElseThrow(()->
                new EntityNotFoundException("No se encontró una multa relacionado al id: " + fineDto.getId()));
        switch(fineDto.getFineState().getValue()){
            case "Apelada", "Pagada":
                if (fine.getFineState().equals(FineState.PENDING)){
                    updateFineState(fine,fineDto);
                } else {
                    throw new IllegalStateException("Transición de "+fine.getFineState() +" a "+fineDto.getFineState()+" No permitida");
                }

                break;
            case "Pendiente de pago", "Absuelta":
                if (fine.getFineState().equals(FineState.APPEALED)){
                    updateFineState(fine,fineDto);
                } else {
                    throw new IllegalStateException("Transición de "+fine.getFineState() +" a "+fineDto.getFineState()+" No permitida");
                }

                break;
            default:
                throw new IllegalStateException("Estado no Reconocido");
        }

        try{
            fineRepository.save(fine);
        } catch (Exception e){
            throw new IllegalStateException("Se produjo un error al intentar guardar la multa: " + e.getMessage(), e);
        }

        return modelMapper.map(fine, GetFineDto.class);
    }

    /**
     * Retrieves the details of a specific fine by its ID.
     * @param id the ID of the fine
     * @return the fine as a DTO
     * @throws EntityNotFoundException
     * if the fine is not found
     */
    @Override
    public GetFineDto getById(Integer id) {
        FineEntity fine = fineRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("No existe la multa con ese id: " + id)
        );

        GetFineDto getFineDto = modelMapper.map(fine, GetFineDto.class);

        DisclaimerEntity disclaimerEntity = disclaimerRepository.findByFineId(getFineDto.getId());
        System.out.println(disclaimerEntity);
        if(disclaimerEntity == null){
            getFineDto.setDisclaimer("No hay reclamo.");
        }  else {
            getFineDto.setDisclaimer(disclaimerEntity.getDisclaimer());
        }


        return getFineDto;
    }
    /**
     * Updates the state of a fine.
     *
     * @param fine the fine entity to update
     * @param fineDto the DTO containing
     * the updated fine state data
     */
    private void updateFineState(FineEntity fine, PutFineStateDto fineDto){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

        fine.setFineState(fineDto.getFineState());
        fine.setStateReason(fine.getStateReason()+"\n"+ sdf.format(new Date())+SEPARATOR+fineDto.getStateReason());
        fine.setLastUpdatedUser(fineDto.getUserId());
        fine.setLastUpdatedDate(LocalDateTime.now());
    }
    /**
     * Scheduled task to update the state of pending fines.
     */
    @Scheduled(cron = "0 0 0 * * *")
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void paymetPaymentStateForPendingFines(){
        this.paymetPaymentStateForPendingFinesLogic();
    }

    /**
     * Logic to update the state of pending fines.
     */
    private void paymetPaymentStateForPendingFinesLogic(){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        List<FineEntity> pendingFines = fineRepository.getFineEntitiesByFineState(FineState.PENDING);
        List<FineEntity> updatedFines = new ArrayList<>();
        Date now = new Date();

        for(FineEntity f :pendingFines){
            if (LocalDate.now().minusDays(7L).equals(f.getCreatedDate().toLocalDate())){
                f.setFineState(FineState.PAYMENT_PAYMENT);
                f.setStateReason(f.getStateReason()+"\n"+ sdf.format(now)+SEPARATOR + "Han pasado 7 Dias desde la emisión de la multa");
                f.setLastUpdatedDate(LocalDateTime.now());

             updatedFines.add(f);
            }
        }
        if (!updatedFines.isEmpty()){
            try{
                fineRepository.saveAll(updatedFines);
            } catch (Exception e){
                throw new IllegalStateException("Se produjo un error al intentar guardar las multas: " + e.getMessage(), e);
            }
        }
    }

    /**
     * Updates a specific fine.
     * @param fineDto the DTO containing the updated fine data
     * @return the updated fine as a DTO
     * @throws EntityNotFoundException
     * if the fine is not found
     */
    @Override
    public GetFineDto updateFine(PutFineDto fineDto) {
        FineEntity fine = fineRepository.findById(fineDto.getId()).orElseThrow(() ->
                new EntityNotFoundException("No se encontró una multa con el id: " + fineDto.getId())
        );
        if (fineDto.getAmount() != null) {
            fine.setAmount(fineDto.getAmount());
        }
        fine.setLastUpdatedDate(LocalDateTime.now());
        fine.setLastUpdatedUser(fineDto.getUserId());

        try {
            FineEntity updatedFine = fineRepository.save(fine);

            return modelMapper.map(updatedFine, GetFineDto.class);
        } catch (Exception e) {
            throw new IllegalStateException("Se produjo un error al intentar actualizar la multa: " + e.getMessage(), e);
        }
    }

    /**Obtiene multas reducidas creadas entre fechas específicas.
     * @param startDate la fecha de inicio
     * @param endDate la fecha de fin
     * @return una lista de multas reducidas como DTOs
     * @throws IllegalArgumentException si la fecha de inicio
     * es posterior o igual a la fecha final*/
    @Override
    public List<GetReducedFineDto> getReducedFinesByCreatedDateBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {

        if (startDate.isAfter(endDate) || startDate.isEqual(endDate)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior o igual a la fecha final");
        }


        List<FineEntity> fines = fineRepository.getFineEntitiesByCreatedDateBetween(startDate, endDate);

        //Nos pidieron que no les tiremos un error si no se encuentran multas
//        if (fines.isEmpty()) {
//            throw new EntityNotFoundException("No se encontraron denuncias entre las fechas: " + startDate + " - " + endDate);
//        }

        List<GetReducedFineDto> finesDtos = new ArrayList<>();
        fines.forEach(f -> {
            GetReducedFineDto fine = new GetReducedFineDto();
            fine.setFine_id(f.getId());
            fine.setAmount(f.getAmount());
            fine.setPlot_id(f.getReport().getPlotId());
            fine.setDescription(f.getReport().getDescription());
            finesDtos.add(fine);
        });

        return finesDtos;
    }
    /**
     * Retrieves all fines.
     *
     * @return a list of all fines as DTOs
     */
    @Override
    public List<GetFineDto> getAllFines(){
        List<FineEntity> finesEntities = fineRepository.findAll();
        List<GetFineDto> finesDto = new ArrayList<>();

        for(FineEntity f : finesEntities){
            finesDto.add(modelMapper.map(f,GetFineDto.class));
        }
        return finesDto;
    }
    /**
     * Retrieves all fine states.
     *
     * @return a map of all fine states
     */
    @Override
    public Map<String, String> getAllFineStates() {
        Map<String, String> fineState = FineState.toMap();
        fineState.put("WARNING","Advertencia");
        return fineState;
    }


    /**
     * Updates the state of a fine to "paid" for fines in "payment pending" state.
     * @param dto the DTO containing the fine ID and payment details
     * @throws EntityNotFoundException if the fine or related report is not found
     * @throws IllegalStateException if the state transition is not allowed
     */
    @Override
    public void changeFineToPaid(FinePaidDto dto){
        //creo un formato de fecha
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

        // Busqueda de la multa
        FineEntity fine = fineRepository.findById(dto.getFineId()).orElseThrow(() ->
                new EntityNotFoundException("No se encontró una denuncia relacionada al id: " + dto.getFineId())
        );

        // Verificación de que la multa se encuentre en estado de pendiente de pago
        if (fine.getFineState() != FineState.PAYMENT_PAYMENT){
            throw new IllegalStateException("Transición de "+fine.getFineState() +" a "+FineState.PAYED+" No permitida");
        }

        // Setea el reportId
        int reportId = fine.getReport().getId();
        // Se busca el reporte relacionado por id
        ReportEntity report = reportRepository.findById(reportId).orElseThrow(() ->
                new EntityNotFoundException("No se encontró un repositorio relacionado al id: "+ reportId));

        // Modificamos el estado de la multa a pagada
        fine.setFineState(FineState.PAYED);
        fine.setStateReason(fine.getStateReason()+"\n"+ sdf.format(new Date())+SEPARATOR+ "La multa fué abonada el dia:" + dto.getDateTo());
        fine.setLastUpdatedDate(LocalDateTime.now()); // Ver si se puede tomar el date del dto y parsearlo
        fine.setLastUpdatedUser(dto.getOwnerId());

        // Modificamos el estado del reporte a finalizado debido a que la multa se pagó
        report.setReportState(ReportState.ENDED);
        report.setStateReason("La multa relacionada a este reporte fue pagada");
        report.setLastUpdatedDate(LocalDateTime.now());
        report.setLastUpdatedUser(dto.getOwnerId());

        // Guardamos los cambios de la multa
        try {
            fineRepository.save(fine);
        } catch (Exception e){
            throw new IllegalStateException("Se produjo un error al intentar actualizar la multa: " + e.getMessage(), e);
        }

        // Guardamos los cambios del reporte
        try {
            reportRepository.save(report);
        } catch (Exception e){
            throw new IllegalStateException("Se produjo un error al intentar actualizar el reporte: " + e.getMessage(), e);
        }
    }
}
