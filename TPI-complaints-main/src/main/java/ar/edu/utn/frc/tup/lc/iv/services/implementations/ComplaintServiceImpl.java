package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.clients.FileManagerClient;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetComplaintDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReducedComplaintDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostComplaintDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutComplaintReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutComplaintStateDto;
import ar.edu.utn.frc.tup.lc.iv.entities.ComplaintEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.PictureEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.enums.ComplaintState;
import ar.edu.utn.frc.tup.lc.iv.repositories.ComplaintRepository;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.ComplaintService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Servicio para gestionar denuncias y sus estados.
 */
@Service
public class ComplaintServiceImpl implements ComplaintService {

    /**
     * Repositorio para acceder a las denuncias en la base de datos.
     */
    @Autowired
    private ComplaintRepository complaintRepository;

    /**
     * Cliente para gestionar archivos (subidas, etc.).
     */
    @Autowired
    private FileManagerClient fileManagerClient;

    /**
     * Utilidad para mapear objetos entre DTOs y entidades.
     */
    private final ModelMapper modelMapper;

    /**
     * Constructor para inicializar el servicio de denuncias.
     *
     * @param complaintRepository Repositorio de denuncias.
     * @param fileManagerClient   Cliente de gestión de archivos.
     * @param modelMapper         Mapper de modelos.
     */
    public ComplaintServiceImpl(ComplaintRepository complaintRepository, FileManagerClient fileManagerClient,
            ModelMapper modelMapper) {
        this.complaintRepository = complaintRepository;
        this.fileManagerClient = fileManagerClient;
        this.modelMapper = modelMapper;
    }

    /**
     * Crea una nueva denuncia.
     *
     * @param postComplaintDto DTO con los datos para crear la denuncia.
     * @return DTO de la denuncia creada.
     */
    @Transactional
    @Override
    public GetComplaintDto createComplaint(PostComplaintDto postComplaintDto) {
        ComplaintEntity complaint = new ComplaintEntity();

        // Mappeo a mano el post a entity
        complaint.setUserId(postComplaintDto.getUserId());
        complaint.setComplaintReason(postComplaintDto.getComplaintReason());
        complaint.setDescription(postComplaintDto.getDescription());

       if ("Otro".equals(postComplaintDto.getComplaintReason())) {
            complaint.setAnotherReason(postComplaintDto.getAnotherReason());
        }

        // Setteo de valores default
        complaint.setComplaintState(ComplaintState.NEW);
        complaint.setStateReason("Nueva denuncia");
        complaint.setCreatedUser(postComplaintDto.getUserId());
        complaint.setCreatedDate(LocalDateTime.now());
        complaint.setLastUpdatedUser(postComplaintDto.getUserId());
        complaint.setLastUpdatedDate(LocalDateTime.now());
        complaint.setPictures(new ArrayList<>());

        // Registro de las imagenes
        for (MultipartFile file : postComplaintDto.getPictures()) {
            PictureEntity picture = new PictureEntity();

            // Rest al micro de FileManager que devuelve un uuid
            String fileId = fileManagerClient.uploadFile(file);
            picture.setPictureUrl(fileId);

            picture.setComplaint(complaint);
            picture.setCreatedUser(postComplaintDto.getUserId());
            picture.setCreatedDate(LocalDateTime.now());
            picture.setLastUpdatedUser(postComplaintDto.getUserId());
            picture.setLastUpdatedDate(LocalDateTime.now());

            complaint.getPictures().add(picture);
        }

        complaintRepository.save(complaint);

        //Mappeo a DTO
        return modelMapper.map(complaint, GetComplaintDto.class);
    }


    /**
     * Obtiene todos los estados de denuncia.
     *
     * @return Mapa de estados de denuncia.
     */
    @Override
    public Map<String, String> getAllComplaintStates() {
        return ComplaintState.toMap();
    }

    /**
     * Obtiene todas las denuncias en un formato reducido.
     *
     * @return Lista de DTOs de denuncias reducidas.
     */
    @Override
    public List<GetReducedComplaintDto> getAllReducedComplaints() {
        List<ComplaintEntity> complaints = complaintRepository.findAll();

        if (complaints.isEmpty()) {
            throw new EntityNotFoundException("El listado de denuncias esta vacio");
        }

        List<GetReducedComplaintDto> complaintsDtos = new ArrayList<>();
        complaints.forEach(c -> {
            GetReducedComplaintDto dto = modelMapper.map(c, GetReducedComplaintDto.class);
            dto.setFileQuantity(c.getPictures().size());
            complaintsDtos.add(dto);
        });

        return complaintsDtos;
    }


    @Override
    public GetComplaintDto updateComplaintState(Integer id, PutComplaintStateDto putComplaintStateDto) {
        ComplaintEntity complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la denuncia con id: " + id));

        complaint.setComplaintState(putComplaintStateDto.getComplaintState());
        complaint.setStateReason(putComplaintStateDto.getStateReason());
        complaint.setLastUpdatedUser(putComplaintStateDto.getUserId());
        complaint.setLastUpdatedDate(LocalDateTime.now());

        complaint = complaintRepository.save(complaint);

        GetComplaintDto dto = modelMapper.map(complaint, GetComplaintDto.class);

        dto.setComplaintState(complaint.getComplaintState().getValue()); // Esto porque???

        return dto;
    }

    /**
     * Obtiene todas las denuncias asociadas a un informe.
     *
     * @param reportId ID del informe.
     * @return Lista de denuncias asociadas al informe.
     */

    @Override
    public List<GetComplaintDto> getComplaintsByReport(Integer reportId) {
        List<ComplaintEntity> complaints = complaintRepository.findAllByReportId(reportId);


        if (complaints.isEmpty()){
            return Collections.emptyList();
        }

        List<GetComplaintDto> lstGetComplaintDto = new ArrayList<>();

        complaints.forEach(c -> lstGetComplaintDto.add(modelMapper.map(c, GetComplaintDto.class)));

        return lstGetComplaintDto;
    }

    /**
     * Actualiza las denuncias anexadas a un informe, desanexando las previas y
     * anexando las nuevas.
     *
     * @param putComplaintReportDto Datos del informe y las denuncias.
     * @return Lista de denuncias actualizadas.
     */
    @Override
    @Transactional
    public List<GetComplaintDto> updateComplaintReport(PutComplaintReportDto putComplaintReportDto) {
        List<GetComplaintDto> lstGetComplaintDto = new ArrayList<>();

        if (putComplaintReportDto.getComplaintIds().isEmpty()) {
            throw new EntityNotFoundException("El listado de denuncias a anexar esta vacio");
        }

        // FixMe: Esto se puede optimizar, lo hago asi para que puedan empezar a usarlo
        // -Gabi
        List<ComplaintEntity> oldComplaints = complaintRepository
                .findAllByReportId(putComplaintReportDto.getReportId());
        for (ComplaintEntity complaint : oldComplaints) {
            complaint.setReportId(null);
            complaint.setComplaintState(ComplaintState.PENDING);
            complaint.setStateReason("Denuncia desanexada");
            complaint.setLastUpdatedDate(LocalDateTime.now());
            complaint.setLastUpdatedUser(putComplaintReportDto.getUserId());
            complaintRepository.save(complaint);
        }

        // Busco todas las denuncias seleccionadas para anexar al informe
        for (Integer complaintId : putComplaintReportDto.getComplaintIds()) {
            ComplaintEntity complaint = complaintRepository.findById(complaintId).orElseThrow(
                    () -> new EntityNotFoundException("No se encontró la denuncia con id: " + complaintId));

            complaint.setReportId(putComplaintReportDto.getReportId());
            complaint.setComplaintState(ComplaintState.ATTACHED);
            complaint.setStateReason("Denuncia anexada");
            complaint.setLastUpdatedUser(putComplaintReportDto.getUserId());
            complaint.setLastUpdatedDate(LocalDateTime.now());
            complaintRepository.save(complaint);

            lstGetComplaintDto.add(modelMapper.map(complaint, GetComplaintDto.class));
        }

        return lstGetComplaintDto;
    }

    /**
     * Obtiene una denuncia por su ID.
     *
     * @param id ID de la denuncia.
     * @return DTO de la denuncia encontrada.
     * @throws EntityNotFoundException Si no se encuentra la denuncia con el ID
     *                                 especificado.
     */
    @Override
    public GetComplaintDto getComplaintById(int id) {
        ComplaintEntity complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la denuncia con id: " + id));

        //Mappeo a DTO
        return modelMapper.map(complaint, GetComplaintDto.class);
    }

}
