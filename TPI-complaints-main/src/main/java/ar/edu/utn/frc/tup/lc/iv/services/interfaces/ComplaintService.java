package ar.edu.utn.frc.tup.lc.iv.services.interfaces;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetComplaintDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReducedComplaintDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostComplaintDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutComplaintReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutComplaintStateDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Interfaz del servicio para gestionar operaciones de las denuncias.
 */
@Service
public interface ComplaintService {

    /**
     * Crea una nueva denuncia.
     *
     * @param postComplaintDto Datos de la denuncia a crear.
     * @return Denuncia creada en formato DTO.
     */
    GetComplaintDto createComplaint(PostComplaintDto postComplaintDto);
    /**
     * Obtiene todos los estados de denuncias disponibles.
     *
     * @return Mapa de estados de denuncias.
     */
    Map<String, String> getAllComplaintStates();

    /**
     * Obtiene una lista reducida de todas las denuncias.
     *
     * @return Lista de denuncias reducidas en formato DTO.
     */
    List<GetReducedComplaintDto> getAllReducedComplaints();


    /**
     * Obtiene una denuncia por su ID.
     *
     * @param id ID de la denuncia.
     * @return Denuncia en formato DTO.
     */
    GetComplaintDto getComplaintById(int id);

    /**
     * Actualiza el estado de una denuncia.
     *
     * @param id ID de la denuncia.
     * @param putComplaintStateDto Datos del nuevo estado.
     * @return Denuncia actualizada en formato DTO.
     */
    GetComplaintDto updateComplaintState(Integer id, PutComplaintStateDto putComplaintStateDto);

    /**
     * Obtiene denuncias asociadas a un informe específico.
     *
     * @param reportId ID del informe.
     * @return Lista de denuncias asociadas en formato DTO.
     */
    List<GetComplaintDto> getComplaintsByReport(Integer reportId);

    /**
     * Actualiza el informe asociado a una o más denuncias.
     *
     * @param putComplaintReportDto Datos del informe a actualizar.
     * @return Lista de denuncias actualizadas en formato DTO.
     */
    List<GetComplaintDto> updateComplaintReport(PutComplaintReportDto putComplaintReportDto);

}
